package com.fenghaze.mybatis.mapper;

import com.fenghaze.mybatis.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
    List<Emp> list(Integer start, Integer pageSize, String empNo, LocalDate startTime, LocalDate endTime);

    @Select("select count(*) from emp")
    Integer count();

    void delete(List<Integer> empIds);

    @Options(useGeneratedKeys = true, keyProperty = "empId")
    void insert(Emp emp);

    @Select("select * from emp where emp_id = #{empId}")
    Emp getById(Integer empId);

    @Select("select * from emp where emp_no = #{empNo} and password = #{password}")
    Emp loginByEmpNo(@Param("empNo") String empNo, @Param("password") String password);

    @Select("select * from emp where phone = #{phone} and password = #{password}")
    Emp loginByPhone(@Param("phone") String phone, @Param("password") String password);

    @Select("select * from emp where name = #{name} and password = #{password}")
    Emp loginByName(@Param("name") String name, @Param("password") String password);

    @Update("update emp set avatar = #{avatar} where emp_id = #{empId}")
    void updateAvatar(Emp emp);

    @Select("select e.* from emp e left join dept d on e.dept_id = d.dept_id where e.emp_id = #{empId}")
    Emp getEmpDetail(Integer empId);

    void updatePartial(Map<String, Object> params);

    @Delete("delete from emp where dept_id = #{deptId}")
    void deleteByDeptId(Integer deptId);
}
