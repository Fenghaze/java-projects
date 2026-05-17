package com.fenghaze.mybatis.config;

import com.fenghaze.mybatis.interceptor.AuthCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthCheckInterceptor authCheckInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor)
                .addPathPatterns("/**") // 这里可以指定需要拦截的路径，例如 "/api/**"
                .excludePathPatterns("/login"); // 这里可以指定不需要拦截的路径，例如 "/login"
    }

}
