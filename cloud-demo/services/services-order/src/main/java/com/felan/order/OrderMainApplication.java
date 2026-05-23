package com.felan.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OrderMainApplication {
  public static void main(String[] args) {
    System.out.println("Hello Order Service!");
    SpringApplication.run(OrderMainApplication.class, args);
  }
}
