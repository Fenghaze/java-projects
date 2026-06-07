package com.felan.usercenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.felan.usercenter.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
  User findByAccountOrEmailOrPhone(
      @Param("account") String account,
      @Param("email") String email,
      @Param("phone") String phone
  );
}
