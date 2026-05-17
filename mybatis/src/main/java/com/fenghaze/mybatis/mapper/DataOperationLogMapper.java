package com.fenghaze.mybatis.mapper;

import com.fenghaze.mybatis.pojo.DataOperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface DataOperationLogMapper {

    @Insert("INSERT INTO data_operation_log (operator, operation_time, class_name, method_name, params, return_value, duration) " +
            "VALUES (#{operator}, #{operationTime}, #{className}, #{methodName}, #{params}, #{returnValue}, #{duration})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(DataOperationLog log);
}
