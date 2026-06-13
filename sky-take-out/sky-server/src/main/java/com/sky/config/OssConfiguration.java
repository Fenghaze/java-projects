package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration // 项目启动时，自动加载这个配置类
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean // 单例模式
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        AliOssUtil utils = new AliOssUtil(
            aliOssProperties.getEndpoint(),
            aliOssProperties.getAccessKeyId(),
            aliOssProperties.getAccessKeySecret(),
            aliOssProperties.getBucketName()
        );
        log.info("开始创建阿里云文件上传客户端对象......{}", utils);
        return utils;
    }
}
