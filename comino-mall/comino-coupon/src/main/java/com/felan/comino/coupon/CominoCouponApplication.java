package com.felan.comino.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.felan.comino.coupon.dao")
@SpringBootApplication
public class CominoCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(CominoCouponApplication.class, args);
    }

}
