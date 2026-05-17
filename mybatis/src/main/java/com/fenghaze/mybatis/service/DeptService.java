package com.fenghaze.mybatis.service;

import com.fenghaze.mybatis.pojo.Dept;

import java.util.List;

public interface DeptService {
    public List<Dept> list();

    public Dept getById(Integer deptId);

    public void deleteById(Integer deptId) throws Exception;

    void insert(Dept dept);
}
