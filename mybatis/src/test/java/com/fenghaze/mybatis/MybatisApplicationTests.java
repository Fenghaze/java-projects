package com.fenghaze.mybatis;

import com.fenghaze.mybatis.service.AuthService;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class MybatisApplicationTests {
    @Autowired
    AuthService authService;
    @Test
    public void testParseJwt() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJlbXBJZCI6MjEsImlhdCI6MTc3ODM4MTcyNiwiZXhwIjoxNzc4NDY4MTI2fQ.9jvKE1Qxz2zvJ--ROrn_PM_1O3rxoz7vNNFD6n8mw_o";
        Map<String, Object> claims = authService.parseJwt(jwt);
        log.info("claims: {}", claims);
    }
}
