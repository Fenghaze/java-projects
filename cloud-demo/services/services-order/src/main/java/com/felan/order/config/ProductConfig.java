package com.felan.order.config;

import feign.Logger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProductConfig {
  @Bean
  @LoadBalanced
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    return restTemplate;
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
