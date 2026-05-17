package com.fenghaze.mybatis.service.impl;

import com.fenghaze.mybatis.mapper.EmpMapper;
import com.fenghaze.mybatis.pojo.Emp;
import com.fenghaze.mybatis.response.PageResult;
import com.fenghaze.mybatis.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    EmpMapper empMapper;
    @Override
    public PageResult list(Integer pageNum, Integer pageSize, String empNo, LocalDate startTime, LocalDate endTime) {
        // Service层计算分页查询的起始索引，传入给Dao层
        Integer start = (pageNum - 1) * pageSize;
        List<Emp> data = empMapper.list(start, pageSize, empNo, startTime, endTime);
        Integer total = empMapper.count();
        return new PageResult(data, total);
    }

    @Override
    public void delEmps(List<Integer> empIds) {
        empMapper.delete(empIds);
    }

    @Override
    public Emp getEmpById(Integer empId) {
        return empMapper.getById(empId);
    }

    @Override
    public void updateEmp(Map<String, Object> fields) {
        empMapper.updatePartial(fields);
    }

    @Override
    public void updateAvatar(Integer empId, String avatarUrl) {
        Emp emp = new Emp();
        emp.setEmpId(empId);
        emp.setAvatar(avatarUrl);
        empMapper.updateAvatar(emp);
    }

    @Override
    public void deleteByDeptId(Integer deptId) {
        empMapper.deleteByDeptId(deptId);
    }

    @Override
    public void addEmp(Emp emp) {
        // 自动生成员工号：EMP + yyyyMMddHHmmssSSS
        String empNo = "EMP" + DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").format(LocalDateTime.now());
        emp.setEmpNo(empNo);

        // 设置默认密码
        if (emp.getPassword() == null || emp.getPassword().isEmpty()) {
            emp.setPassword("123456");
        }

        empMapper.insert(emp);
    }
}
