package com.fenghaze.mybatis.exception;

import com.fenghaze.mybatis.response.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = Exception.class)
  public Result ex(Exception e) {
    e.printStackTrace();
    return Result.error(e.getMessage());
  }
}
