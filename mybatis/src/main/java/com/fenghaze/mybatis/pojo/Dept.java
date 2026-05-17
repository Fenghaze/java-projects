package com.fenghaze.mybatis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept {
    private Integer deptId;
    private String deptName;
    private String deptLoc;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
