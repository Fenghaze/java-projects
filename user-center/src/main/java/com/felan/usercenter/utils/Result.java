package com.felan.usercenter.utils;

import lombok.Data;

@Data
public class Result {
  private String code;
  private String msg;
  private Object data;

  public Result() {}

  public Result(String code, String msg, Object data) {
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  // getter、setter

  public static Result success(String msg, Object data) {
    return new Result("0000", "success", data);
  }

  public static Result success() {
    return new Result("0000", "success", null);
  }

  public static Result error(String msg) {
    return new Result("1000", msg, null);
  }

  @Override
  public String toString() {
    return "Result{" +
        "code=" + code +
        ", msg='" + msg + '\'' +
        ", data=" + data +
        '}';
  }

  public boolean isSuccess() {
    return this.code.equals("0000");
  }
}
