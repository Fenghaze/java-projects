package com.fenghaze.mybatis.mapper;

import com.fenghaze.mybatis.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Select(value = "select * from dept")
    List<Dept> list();

    @Select("select * from dept where dept_id = #{deptId}")
    Dept getById(Integer deptId);

    @Insert("insert into dept (dept_name, dept_loc) values (#{deptName}, #{deptLoc})")
    @Options(useGeneratedKeys = true, keyProperty = "deptId")
    void insert(Dept dept);

    @Update("update dept set dept_name = #{deptName}, dept_loc = #{deptLoc} where dept_id = #{deptId}")
    void update(Dept dept);

    @Delete("delete from dept where dept_id = #{deptId}")
    void delete(Integer deptId);
}
