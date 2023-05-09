package com.example.kabesystem.interceptor;

import com.auth0.jwt.interfaces.Claim;
import com.example.kabesystem.service.UserAccountService;
import com.example.kabesystem.util.JWTUtil;
import com.example.kabesystem.util.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** 拦截器 */
@Component
public class TokenCheckInterceptor implements HandlerInterceptor {

  private final UserAccountService userAccountService;

  public TokenCheckInterceptor(UserAccountService userAccountService) {
    this.userAccountService = userAccountService;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }

    String authorization = request.getHeader("authorization");
    String bearerToken = JWTUtil.bearerToken(authorization);
    String token = (bearerToken != null) ? bearerToken : authorization;

    Map<String, Claim> claimMap = userAccountService.verifyToken(token);

    if (claimMap == null) {
      response.setCharacterEncoding("UTF-8");
      response.setContentType("application/json; charset=utf-8");
      response.getWriter().append(Result.failure(401, "身份验证凭证过期", null).toString());
      return false;
    }

    request.setAttribute("token", token);
    request.setAttribute("userId", claimMap.get("userId").asLong());
    request.setAttribute("isUploader", claimMap.get("isUploader").asBoolean());
    request.setAttribute("isAdmin", claimMap.get("isAdmin").asBoolean());
    return true;
  }
}
