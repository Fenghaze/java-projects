package com.felan.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.felan.usercenter.model.User;
import com.felan.usercenter.utils.Result;

public interface UserService extends IService<User> {
  Result login(User user);
  Result register(User user);
}
