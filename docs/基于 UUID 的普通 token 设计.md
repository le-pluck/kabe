# 基于 UUID 的普通 token 设计

## token 在后端的产生

``` java
@RestController
@RequestMapping("user-token")
public class UserTokenController {
    private final UserTokenService userTokenService;

    public UserTokenController(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @PostMapping("login")
    public Result<?> login(@RequestBody UserAccount userAccount) {
        Map<String, Object> map = userTokenService.verifyPassword(userAccount.getUsername(), userAccount.getPassword());
        if (((Integer)map.get("code")).equals(200) ) {
            return Result.success(userTokenService.setLogin((Long)map.get("id")));
        } else {
            return Result.failure((int)map.get("code"), (String)map.get("msg"), null);
        }
    }
}
```

通过 `userTokenService.verifyPassword` 方法验证用户账号密码通过后，调用 `userTokenService.setLogin((Long)map.get("id"))`。

那么，`userTokenService.setLogin` 做了什么呢？

``` java
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    private final UserAccountService userAccountService;

    public UserTokenServiceImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // ...

    @Override
    public Map<String, Object> setLogin(Long userId) {
        UserToken userToken = new UserToken(
                null,
                UUID.randomUUID().toString(),
                userId,
                new Timestamp(new Date().getTime() + 86400000),
                false,
                new Timestamp(new Date().getTime())
        );
        baseMapper.insert(userToken);

        // 返回 token
        Map<String, Object> map = new HashMap<>();
        map.put("token", userToken.getToken());
        map.put("userId", userToken.getUserId());
        return map;
    }

    // ...
}
```

可以看到，`setLogin` 通过 `UUID.randomUUID().toString()` 产生了一个唯一的串，作为 token 返回。

## token 在后端的存储

1. 如果存到运行时内存中，做不了分布式，只能单服务器，不实用。
2. 如果存到数据库中，会比存到 Redis 中稍慢一些。（本文这里采用的是存到数据库中）
3. 如果存到 Redis 中，是比较常用的方案。但是每次请求过来都需要查一下 Redis。
4. 如果用 JWT，将存储访问压力转移到客户端，服务器只需要持有一个私钥即可。唯一的缺点是每次请求传输流量会稍大一点点。

笔者打算从方案 2 迁移到方案 4。

这里用的是方案 2，代码可以在上文的 `userTokenService.setLogin` 实现中看到。如下：

``` java
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    // ...

    public Map<String, Object> setLogin(Long userId) {
        // ...

        baseMapper.insert(userToken);

        // ...
    }

    // ...
}
```


## token 在前端的存储

在前端收到 token 后，将其保存到 localStorage。

``` ts
const login = async () => {
  if (!formValidation.valid) return;
  const data = await userTokenApi.login({ username: userAccount.username, password: userAccount.password });
  localStorage.setItem("token", data.token);
};
```

## 请求中 token 的携带

在前端发送请求时，通过请求拦截器，将 token 加入到 Headers 中。

``` ts
// ...

const instance: AxiosInstance = axios.create({
  baseURL: "/api",
  timeout: 30000,
  // ...
});

instance.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers["token"] = token;
    }
    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

// ...
```

## 处理带 token 的请求

声明拦截器

``` java
@Component
public class TokenCheckInterceptor implements HandlerInterceptor {
    // ...
}
```

注册拦截器

``` java
@Configuration
public class TokenCheckInterceptorConfig implements WebMvcConfigurer {

    private final TokenCheckInterceptor tokenCheckInterceptor;

    public TokenCheckInterceptorConfig(TokenCheckInterceptor tokenCheckInterceptor) {
        this.tokenCheckInterceptor = tokenCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenCheckInterceptor)
                .addPathPatterns("/user-token/**")
                .addPathPatterns("/user-account/**")
                // ...
                .excludePathPatterns("/user-token/login");
    }
}
```

重写拦截器 `preHandle` 方法

``` java
@Component
public class TokenCheckInterceptor implements HandlerInterceptor {

    private final UserTokenService userTokenService;

    public TokenCheckInterceptor(UserTokenService userTokenService) {
        this.userTokenService = userTokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        String token = request.getHeader("token");
        UserToken userToken = userTokenService.verifyToken(token);
        if (userToken == null) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().append(Result.failure(555, "身份验证过期", null).toString());
            return false;
        }

        request.setAttribute("token", userToken.getToken());
        request.setAttribute("userId", userToken.getUserId());
        return true;
    }
}
```

其中 `userTokenService.verifyToken(token)` 的实现如下：

``` java
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenMapper, UserToken> implements UserTokenService {
    private final UserAccountService userAccountService;

    public UserTokenServiceImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserToken verifyToken(String token) {
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token", token);
        queryWrapper.eq("removed", false);
        return baseMapper.selectOne(queryWrapper);
    }
}
```

如此之后，在 controller 中，可以如此获取到用户的相关信息：

``` java
    @PostMapping("xxx")
    public Result<?> xxx(@RequestAttribute Long userId, @RequestAttribute String token) {
        // ...
    }
```

因此前端只需要存储用户的 token 即可，不需要存储用户的 id。但是发送请求的时候，后端一样能拿到 userId。
