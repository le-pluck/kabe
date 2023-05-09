package com.example.kabesystem.configuration;

import com.example.kabesystem.interceptor.TokenCheckInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 注册拦截器 */
@Configuration
public class TokenCheckInterceptorConfig implements WebMvcConfigurer {

    private final TokenCheckInterceptor tokenCheckInterceptor;

    public TokenCheckInterceptorConfig(TokenCheckInterceptor tokenCheckInterceptor) {
        this.tokenCheckInterceptor = tokenCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenCheckInterceptor)
                .addPathPatterns("/user/**")
                .addPathPatterns("/post/**")
                .addPathPatterns("/mail/**")
                .addPathPatterns("/tag/**")
                .addPathPatterns("/comment/**")
                .addPathPatterns("/submission/**")
                .excludePathPatterns("/user/account/token")
                .excludePathPatterns("/mail/verification");
    }
}
