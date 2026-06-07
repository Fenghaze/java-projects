package com.felan.usercenter.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.felan.usercenter.model.User;
import org.apache.commons.lang3.StringUtils;

/**
 * 用户查询条件构建器 — 将 QueryWrapper 构建逻辑从 Service 层剥离到数据访问层
 */
public class UserQueryBuilder {

  /**
   * 构建用户列表的分页查询条件
   * <pre>
   * 规则：
   *  - account / username → LIKE 模糊匹配
   *  - email / phone      → = 精确匹配
   *  - gender / userStatus → = 精确匹配（null 时忽略）
   *  - 默认按 create_time 降序
   * </pre>
   */
  public static QueryWrapper<User> buildListWrapper(User user) {
    QueryWrapper<User> wrapper = new QueryWrapper<>();
    if (StringUtils.isNotBlank(user.getAccount())) {
      wrapper.like("account", user.getAccount());
    }
    if (StringUtils.isNotBlank(user.getUsername())) {
      wrapper.like("username", user.getUsername());
    }
    if (StringUtils.isNotBlank(user.getEmail())) {
      wrapper.eq("email", user.getEmail());
    }
    if (StringUtils.isNotBlank(user.getPhone())) {
      wrapper.eq("phone", user.getPhone());
    }
    if (user.getGender() != null) {
      wrapper.eq("gender", user.getGender());
    }
    if (user.getUserStatus() != null) {
      wrapper.eq("user_status", user.getUserStatus());
    }
    if (user.getRole() != null) {
      wrapper.eq("role", user.getRole());
    }
    wrapper.orderByDesc("create_time");
    return wrapper;
  }
}
