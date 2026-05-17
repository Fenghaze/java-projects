package com.fenghaze.mybatis.service;

import com.fenghaze.mybatis.pojo.Emp;
import com.fenghaze.mybatis.response.PageResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface EmpService {
    PageResult list(Integer pageNum, Integer pageSize, String empNo, LocalDate startTime, LocalDate endTime);

    void delEmps(List<Integer> empIds);

    void addEmp(Emp emp);

    Emp getEmpById(Integer empId);

    void updateEmp(Map<String, Object> fields);

    void updateAvatar(Integer empId, String avatarUrl);

    void deleteByDeptId(Integer deptId);
}
