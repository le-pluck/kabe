# PROPOSAL

一些提案是值得考虑的

## Custom requireToken annotation

自定义 `@requireToken` 注解

### Status （状态）

Stage 1 : Proposal（提案）

### Motivation （动机）

目前是通过 `addPathPatterns()` 和 `excludePathPatterns()` 来管理拦截器的拦截范围。

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
                // .addPathPatterns(/* path of other apis*/)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/account/token");
    }
}
```

使用拦截器去配置拦截要做到细粒度的设置稍显麻烦，而改用自定义注解来控制权限，可以提高写代码的体验、提高代码可读性。

``` java
@RestController
@RequestMapping("/user/account")
@RequireLogin
public class UserAccountController {

    @RequireLogin(required = false)
    @PostMapping("/token")
    public Result<?> postToken(@RequestBody UserAccount userAccount) {
        // ...
    }

    @RequireLogin
    @PutMapping("/password")
    public Result<?> putPassword(@RequestBody UserAccount userAccount) {
        // ...
    }
```

如上，在登录接口 `/token` 配置 `@RequireLogin(required = false)`，设定对此接口的访问不拦截。在修改密码接口 `/password` 配置 `@RequireLogin` 或 `@RequireLogin(required = true)`，设定对此接口的访问进行拦截。

这样在实现每个接口代码时，可以通过 `@RequireLogin` 就地配置是否开启 token 拦截，非常方便。

### Champions （推动者）

+ Pluck ([github@le-pluck](https://github.com/le-pluck), [gitee@pluck7](https://gitee.com/pluck7))