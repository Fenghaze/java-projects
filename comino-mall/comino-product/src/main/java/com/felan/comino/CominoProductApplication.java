package com.felan.comino;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.felan.comino.product.dao")
@SpringBootApplication
public class CominoProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(CominoProductApplication.class, args);
    }

}
