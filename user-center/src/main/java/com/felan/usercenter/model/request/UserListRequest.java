package com.felan.usercenter.model.request;

import lombok.Data;

/**
 * 用户列表查询请求 DTO
 */
@Data
public class UserListRequest {
  // ========== 查询条件 ==========
  private String account;
  private String username;
  private String email;
  private String phone;
  private Integer gender;
  private Integer userStatus;
  private Integer role;
  // ========== 分页参数 ==========
  private Integer page = 1;
  private Integer size = 10;
}
