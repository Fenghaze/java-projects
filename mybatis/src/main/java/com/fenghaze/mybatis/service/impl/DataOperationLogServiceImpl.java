package com.fenghaze.mybatis.service.impl;

import com.fenghaze.mybatis.mapper.DataOperationLogMapper;
import com.fenghaze.mybatis.pojo.DataOperationLog;
import com.fenghaze.mybatis.service.DataOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataOperationLogServiceImpl implements DataOperationLogService {

    @Autowired
    private DataOperationLogMapper dataOperationLogMapper;

    @Override
    public void save(DataOperationLog log) {
        dataOperationLogMapper.insert(log);
    }
}
