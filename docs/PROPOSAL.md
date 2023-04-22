# PROPOSAL

一些提案是值得考虑的

## Custom requireToken annotation （自定义 requireToken 注解）

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

## Elegant exception handling （优雅的异常处理）

### Status （状态）

Stage 1 : Proposal（提案）

### Motivation （动机）

目前我的后端程序完全没有处理任何异常，这是不合理的。

### References （参考）

+ [Spring Boot项目优雅的全局异常处理方式 | 掘金](https://juejin.cn/post/6959520793063587848)
+ [SpringBoot异常处理看这篇就够了 | FleyX's Blog](https://blog.fleyx.com/blog/detail/20210927/)

### Champions （推动者）

+ Pluck ([github@le-pluck](https://github.com/le-pluck), [gitee@pluck7](https://gitee.com/pluck7))

## Tourist visit （游客访问）

### Status （状态）

Stage 1 : Proposal（提案）

### Motivation （动机）

目前我们的网站是未经登录不可访问的。（只能访问登录和注册界面）

作为一个论坛性质的网站，应该允许未经登录的帖子浏览。只有涉及发布帖子、收藏帖子等操作时让用户登录。

### References （参考）

+ [路由元信息 | Vue Router](https://router.vuejs.org/zh/guide/advanced/meta.html)

### Champions （推动者）

+ Pluck ([github@le-pluck](https://github.com/le-pluck), [gitee@pluck7](https://gitee.com/pluck7))


## Ultimate Tag （终极标签）

### Status （状态）

Stage 1 : Proposal（提案）

### Motivation （动机）

+ 图标
    + 可以设计一个合适的数据结构，来让我们的标签有图标。还可以提供用户新建标签时，添加合适的图标。
+ 话题量 / 热度
    + 为文章添加 Tag 时，对用户输入的模糊搜索结果按照标签话题量/热度排序，并显示话题量/热度。
    + 注：话题量是 *(话题量丨有史以来)*，热度是 *(话题量丨一周内)*。
    + 扩展：热度或许可以让用户选择查看 一周内、一个月内 等等。但是这样的设计反而可能让交互变复杂。

### References （参考）

+ [Material Design Icons - Icon Library - Pictogrammers](https://pictogrammers.com/library/mdi/)

### Champions （推动者）

+ Pluck ([github@le-pluck](https://github.com/le-pluck), [gitee@pluck7](https://gitee.com/pluck7))

<style>h3{opacity: 0.75;} h4{opacity: 0.5;} h5{opacity: 0.3;}</style>