package com.fenghaze.mybatis.controller;

import com.fenghaze.mybatis.pojo.Dept;
import com.fenghaze.mybatis.response.Result;
import com.fenghaze.mybatis.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;


@Slf4j
@RestController
public class DeptControlller {
    @Autowired
    DeptService deptService;
    @GetMapping("/api/depts")
    public Result getDepts() {
        log.info("查询部门列表");
        return Result.success(deptService.list());
    }

    @GetMapping("/api/depts/{deptId}")
    public Result getById(@PathVariable Integer deptId) {
        log.info("查询部门详情，deptId={}", deptId);
        return Result.success(deptService.getById(deptId));
    }

    @DeleteMapping("/api/depts/{deptId}")
    public Result deleteById(@PathVariable Integer deptId) throws Exception {
        log.info("删除部门，deptId={}", deptId);
        deptService.deleteById(deptId);
        return Result.success();
    }

    @PostMapping("/api/depts")
    public Result insert(@RequestBody Dept dept) {
        log.info("添加部门");
        deptService.insert(dept);
        return Result.success();
    }
}
