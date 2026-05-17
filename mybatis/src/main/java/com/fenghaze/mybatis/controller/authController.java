package com.fenghaze.mybatis.controller;

import com.fenghaze.mybatis.pojo.Emp;
import com.fenghaze.mybatis.response.Result;
import com.fenghaze.mybatis.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class authController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result login(String account, String password) {
      log.info("account: {}, password: {}", account, password);
      Emp emp = authService.login(account, password);
      if (emp != null) {
        String token = authService.genJwt(emp.getEmpId());
        return Result.success(Map.of("token", token));
      }
      return Result.error("账号或密码错误");
    }
}
