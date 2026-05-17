package com.fenghaze.mybatis.filter;

import com.fenghaze.mybatis.service.AuthService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthFilter implements Filter {
  @Autowired
  private AuthService authService;
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // 这里可以添加对请求的认证逻辑，例如检查 JWT token 是否有效
    log.info("AuthFilter: 请求认证中...");
    // 解析request
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    if (httpRequest.getRequestURL().toString().contains("/login")) {
      chain.doFilter(request, response);
      return;
    }
    // httpResponse.setContentType("text/html;charset=utf-8");
    String jwt = httpRequest.getHeader("token");
    if (jwt == null) {
      chain.doFilter(request, response);
//      log.info("AuthFilter: 请求缺少 token");
//      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//      httpResponse.getWriter().write("请求缺少 token");
      return;
    }
    try {
      Map<String, Object> claims = authService.parseJwt(jwt);
      // 可以将解析出的用户信息存储在请求属性中，供后续处理使用
      httpRequest.setAttribute("claims", claims);
      log.info("AuthFilter: 请求认证成功，claims: {}", claims);
      chain.doFilter(request, response);
    } catch (Exception e) {
      log.info("AuthFilter: 请求认证失败，异常信息: {}", e.getMessage());
      httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      httpResponse.setContentType("application/json;charset=utf-8");
      httpResponse.getWriter().write("{\"code\":401,\"msg\":\"未认证的授权\"}");
    }
  }
}
