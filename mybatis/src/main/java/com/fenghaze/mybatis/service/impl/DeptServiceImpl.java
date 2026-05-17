package com.fenghaze.mybatis.service.impl;

import com.fenghaze.mybatis.mapper.DeptMapper;
import com.fenghaze.mybatis.mapper.EmpMapper;
import com.fenghaze.mybatis.pojo.Dept;
import com.fenghaze.mybatis.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeptServiceImpl implements DeptService {

  @Autowired
  private DeptMapper deptMapper;
  @Autowired
  private EmpMapper empMapper;

  @Override
  public List<Dept> list() {
    return deptMapper.list();
  }

  @Override
  public Dept getById(Integer deptId) {
    return deptMapper.getById(deptId);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(Integer deptId) {
    deptMapper.delete(deptId);
    // 删除部门下的所有员工
    empMapper.deleteByDeptId(deptId);
  }

  @Override
  public void insert(Dept dept) {
    deptMapper.insert(dept);
  }
}
