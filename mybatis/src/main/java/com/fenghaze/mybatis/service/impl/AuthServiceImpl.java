package com.fenghaze.mybatis.service.impl;

import com.fenghaze.mybatis.mapper.EmpMapper;
import com.fenghaze.mybatis.pojo.Emp;
import com.fenghaze.mybatis.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private EmpMapper empMapper;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Override
    public Emp login(String account, String password) {
        // 支持通过用户名、手机号、员工号三种方式登录
        Emp emp = empMapper.loginByName(account, password);
        if (emp != null) return emp;

        emp = empMapper.loginByPhone(account, password);
        if (emp != null) return emp;

        return empMapper.loginByEmpNo(account, password);
    }

    @Override
    public String genJwt(Integer empId) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Map<String, Object> claims = new HashMap<>();
        claims.put("empId", empId);
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    @Override
    public Map<String, Object> parseJwt(String jwt) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return new HashMap<>(claims);
    }
}
