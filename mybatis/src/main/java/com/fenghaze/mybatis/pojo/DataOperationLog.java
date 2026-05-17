package com.fenghaze.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataOperationLog {
    private Long id;
    private String operator;
    private LocalDateTime operationTime;
    private String className;
    private String methodName;
    private String params;
    private String returnValue;
    private Long duration;
}
