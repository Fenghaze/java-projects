package com.felan.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.felan.usercenter.mapper")
public class UserCenterApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserCenterApplication.class, args);
    System.out.println("Hello User Center eeee!");
  }

}
