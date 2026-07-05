package com.felan.comino.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@MapperScan("com.felan.comino.order.dao")
@SpringBootApplication
public class CominoOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CominoOrderApplication.class, args);
    }

}
