package com.fenghaze.mybatis.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fenghaze.mybatis.pojo.DataOperationLog;
import com.fenghaze.mybatis.service.DataOperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class DataOperationLogAspect {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private DataOperationLogService logService;

    // 此AOP方法将会应用在标注了 @LogDataOperation 注解的切入点方法
    @Around("@annotation(com.fenghaze.mybatis.aop.LogDataOperation)")
    public Object logOperation(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed(); // 执行目标方法
        long duration = System.currentTimeMillis() - startTime;

        // 获取切入点相关信息
        try {
            DataOperationLog logRecord = new DataOperationLog();
            logRecord.setOperator(getCurrentOperator());
            logRecord.setOperationTime(LocalDateTime.now());
            logRecord.setClassName(pjp.getTarget().getClass().getName());
            logRecord.setMethodName(pjp.getSignature().getName());
            logRecord.setParams(toJson(pjp.getArgs()));
            logRecord.setReturnValue(toJson(result));
            logRecord.setDuration(duration);
            // 操作日志保存到数据库
            logService.save(logRecord);
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }

        // 返回目标方法的执行结果
        return result;
    }

    private String getCurrentOperator() {
        // TODO: 从当前登录上下文中获取操作人（如 SecurityContext / JWT Token）
        return "system";
    }

    private String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            return obj.toString();
        }
    }
}
