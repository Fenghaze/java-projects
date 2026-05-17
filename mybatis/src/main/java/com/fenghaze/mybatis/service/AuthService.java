package com.fenghaze.mybatis.service;

import com.fenghaze.mybatis.pojo.Emp;

import java.util.Map;

public interface AuthService {
    Emp login(String account, String password);
    String genJwt(Integer empId);
    Map<String, Object> parseJwt(String jwt);
}
