package com.felan.comino.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.felan.comino.member.dao")
@SpringBootApplication
public class CominoMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(CominoMemberApplication.class, args);
    }

}
