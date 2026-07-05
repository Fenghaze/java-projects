package com.felan.comino.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.felan.comino.ware.dao")
@SpringBootApplication
public class CominoWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(CominoWareApplication.class, args);
    }

}
