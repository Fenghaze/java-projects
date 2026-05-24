package com.felan.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class XTokenRequestInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate requestTemplate) {
    System.out.println("XTokenRequestInterceptor open...");
    requestTemplate.header("X-Token", "123456");
  }
}
