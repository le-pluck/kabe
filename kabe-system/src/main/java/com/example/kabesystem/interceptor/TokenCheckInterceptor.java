package com.example.kabesystem.interceptor;

import com.example.kabesystem.model.user.UserToken;
import com.example.kabesystem.service.user.UserTokenService;
import com.example.kabesystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截器
 * */
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
