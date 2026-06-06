package com.felan.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc_v6x.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class MyBlockExceptionHandler implements BlockExceptionHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, String resourceName,
      BlockException e) throws Exception {
      // 设置响应状态码为429（Too Many Requests）
      response.setStatus(429);
      // 设置响应内容类型为JSON
      response.setContentType("application/json;charset=UTF-8");
      // 返回自定义的JSON响应体
      String jsonResponse = "{\"code\":429,\"message\":\"请求过于频繁，请稍后再试！\"}";
      response.getWriter().write(jsonResponse);
  }
}
