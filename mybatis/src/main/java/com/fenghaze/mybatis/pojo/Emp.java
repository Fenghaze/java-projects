package com.fenghaze.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    private Integer empId;
    private String empNo;
    private String password;
    private String avatar;
    private String job;
    private LocalDate hiredate;
    private BigDecimal sal;
    private Integer deptId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String name;
    private Short age;
    private Short gender;
    private String phone;
}
