package com.felan.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductMainApplication {
  public static void main(String[] args) {
    System.out.println("Hello Product Service!");
    SpringApplication.run(ProductMainApplication.class, args);
  }
}