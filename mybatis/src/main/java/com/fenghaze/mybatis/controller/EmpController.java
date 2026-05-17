package com.fenghaze.mybatis.controller;

import com.fenghaze.mybatis.pojo.Emp;
import com.fenghaze.mybatis.response.Result;
import com.fenghaze.mybatis.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/emps")
public class EmpController {
    @Autowired
    EmpService empService;

    /**
     * @param pageNum 当前页码
     * @param pageSize 分页条数
     * @param empNo 员工工号
     * @param startTime 员工入职时间开始日期
     * @param endTime 员工入职时间结束日期
     * @return
     */
    @GetMapping
    public Result getEmpsList(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              String empNo,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startTime,
                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endTime) {
        log.info("查询员工列表，pageNum={}, pageSize={}, empNo={}, startTime={}, endTime={}", pageNum, pageSize, empNo, startTime, endTime);
        return Result.success(empService.list(pageNum, pageSize, empNo, startTime, endTime));
    }

    @DeleteMapping("/{empIds}")
    public Result delEmpsList(@PathVariable List<Integer> empIds) {
        log.info("批量删除员工, empIds: {}", empIds);
        empService.delEmps(empIds);
        return Result.success();
    }

    @GetMapping("/{empId}")
    public Result getEmpById(@PathVariable Integer empId) {
        log.info("查询员工详情, empId: {}", empId);
        Emp emp = empService.getEmpById(empId);
        return Result.success(emp);
    }

    @PutMapping("/{empId}")
    public Result updateEmp(@PathVariable Integer empId, @RequestBody Map<String, Object> fields) {
        fields.put("empId", empId);
        log.info("更新员工, empId: {}, fields: {}", empId, fields);
        empService.updateEmp(fields);
        return Result.success();
    }

    @PostMapping
    public Result addEmp(@RequestBody Emp emp) {
        log.info("添加员工, emp: {}", emp);
        empService.addEmp(emp);
        return Result.success();
    }
}
