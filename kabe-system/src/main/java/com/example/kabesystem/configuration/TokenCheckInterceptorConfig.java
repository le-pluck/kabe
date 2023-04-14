package com.example.kabesystem.configuration;

import com.example.kabesystem.interceptor.TokenCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 * */
@Configuration
public class TokenCheckInterceptorConfig implements WebMvcConfigurer {

    private final TokenCheckInterceptor tokenCheckInterceptor;

    public TokenCheckInterceptorConfig(TokenCheckInterceptor tokenCheckInterceptor) {
        this.tokenCheckInterceptor = tokenCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenCheckInterceptor)
                .addPathPatterns("/user/account/**")
                .excludePathPatterns("/user/account/token");
    }
}