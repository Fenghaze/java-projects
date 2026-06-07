package com.felan.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.felan.usercenter.mapper.UserMapper;
import com.felan.usercenter.mapper.UserQueryBuilder;
import com.felan.usercenter.model.User;
import com.felan.usercenter.service.UserService;
import com.felan.usercenter.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
  private static final String SALT = "felan";
  private static final int ACCOUNT_MIN_LENGTH = 4;
  private static final int PASSWORD_MIN_LENGTH = 8;
  private static final String ACCOUNT_PATTERN = "^[a-zA-Z0-9_-]{4,16}$";
  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
  private static final String PHONE_PATTERN = "^1[3-9][0-9]{9}$";

  @Autowired
  private UserMapper userMapper;

  // ========== 公开方法 ==========

  @Override
  public Result login(User user) {
    String account = user.getAccount();
    String password = user.getPassword();
    String email = user.getEmail();
    String phone = user.getPhone();
    // 至少需要提供一种标识和密码
    if (StringUtils.isBlank(password)
        || (StringUtils.isBlank(account) && StringUtils.isBlank(email) && StringUtils.isBlank(phone))) {
      return Result.error("账号或密码不能为空");
    }
    // 对传入的标识和密码做格式校验
    Result passwordCheck = validatePasswordFormat(password);
    if (!passwordCheck.isSuccess()) {
      return passwordCheck;
    }
    if (StringUtils.isNotBlank(account)) {
      Result accountCheck = validateAccountFormat(account);
      if (!accountCheck.isSuccess()) {
        return accountCheck;
      }
    }
    if (StringUtils.isNotBlank(email)) {
      Result emailCheck = validateEmailFormat(email);
      if (!emailCheck.isSuccess()) {
        return emailCheck;
      }
    }
    if (StringUtils.isNotBlank(phone)) {
      Result phoneCheck = validatePhoneFormat(phone);
      if (!phoneCheck.isSuccess()) {
        return phoneCheck;
      }
    }
    // 一次查询匹配任意标识
    User loginUser = userMapper.findByAccountOrEmailOrPhone(account, email, phone);
    if (loginUser == null || loginUser.getIsDelete() == 1) {
      return Result.error("账号不存在");
    }
    if (!setSecurePassword(password).equals(loginUser.getPassword())) {
      return Result.error("账号或密码错误");
    }
    return Result.success("登录成功", loginUser.getId());
  }

  @Override
  public Result register(User user) {
    Result validAccountRes = validAccount(user);
    if (!validAccountRes.isSuccess()) {
      return validAccountRes;
    }
    Result validEmailRes = validEmail(user);
    if (!validEmailRes.isSuccess()) {
      return validEmailRes;
    }
    Result validPhoneRes = validPhone(user);
    if (!validPhoneRes.isSuccess()) {
      return validPhoneRes;
    }
    Result validPasswordRes = validPassword(user);
    if (!validPasswordRes.isSuccess()) {
      return validPasswordRes;
    }
    // 新注册用户默认为普通角色
    user.setRole(0);
    // 用户密码加密
    String securePassword = setSecurePassword(user.getPassword());
    user.setPassword(securePassword);
    Integer result = userMapper.insert(user);
    if (result <= 0) {
      return Result.error("注册失败");
    }
    return Result.success("注册成功", user.getId());
  }

  @Override
  public Result deleteById(Long id, Long operatorId) {
    // 校验操作人是否为管理员
    User operator = userMapper.selectById(operatorId);
    if (operator == null || !Integer.valueOf(1).equals(operator.getRole())) {
      return Result.error("无权限：仅管理员可执行删除操作");
    }
    // `@TableLogic` 会由 deleteById 自动将 DELETE 转为 UPDATE 设置 is_delete=1
    int rows = userMapper.deleteById(id);
    if (rows <= 0) {
      return Result.error("用户不存在");
    }
    return Result.success("删除成功", null);
  }

  @Override
  public Result listUsers(User user, Integer pageNum, Integer pageSize) {
    // 默认页码和大小
    if (pageNum == null || pageNum < 1) {
      pageNum = 1;
    }
    if (pageSize == null || pageSize < 1) {
      pageSize = 10;
    }
    // 查询条件由 UserQueryBuilder 构建
    QueryWrapper<User> wrapper = UserQueryBuilder.buildListWrapper(user);
    // 分页查询
    Page<User> page = new Page<>(pageNum, pageSize);
    Page<User> result = userMapper.selectPage(page, wrapper);
    return Result.success("查询成功", result);
  }

  // ========== 格式校验（纯静态校验，不含 DB 操作） ==========

  private Result validateAccountFormat(String account) {
    if (StringUtils.isBlank(account)) {
      return Result.error("账号不能为空");
    }
    if (account.length() < ACCOUNT_MIN_LENGTH) {
      return Result.error("账号长度不能小于4位");
    }
    if (!account.matches(ACCOUNT_PATTERN)) {
      return Result.error("账号格式错误");
    }
    return Result.success();
  }

  private Result validatePasswordFormat(String password) {
    if (StringUtils.isBlank(password)) {
      return Result.error("密码不能为空");
    }
    if (password.length() < PASSWORD_MIN_LENGTH) {
      return Result.error("密码长度不能小于8位");
    }
    return Result.success();
  }

  private Result validateEmailFormat(String email) {
    if (StringUtils.isBlank(email)) {
      return Result.error("邮箱不能为空");
    }
    if (!email.matches(EMAIL_PATTERN)) {
      return Result.error("邮箱格式错误");
    }
    return Result.success();
  }

  private Result validatePhoneFormat(String phone) {
    if (StringUtils.isBlank(phone)) {
      return Result.error("手机号不能为空");
    }
    if (!phone.matches(PHONE_PATTERN)) {
      return Result.error("手机号格式错误");
    }
    return Result.success();
  }

  // ========== 注册专用校验（格式校验 + DB 唯一性检查） ==========

  private Result validAccount(User user) {
    String account = user.getAccount();
    Result formatRes = validateAccountFormat(account);
    if (!formatRes.isSuccess()) {
      return formatRes;
    }
    if (userMapper.selectOne(new QueryWrapper<User>().eq("account", account)) != null) {
      return Result.error("账号已存在");
    }
    return Result.success();
  }

  private Result validPassword(User user) {
    return validatePasswordFormat(user.getPassword());
  }

  private Result validEmail(User user) {
    String email = user.getEmail();
    Result formatRes = validateEmailFormat(email);
    if (!formatRes.isSuccess()) {
      return formatRes;
    }
    if (userMapper.selectOne(new QueryWrapper<User>().eq("email", email)) != null) {
      return Result.error("邮箱已存在");
    }
    return Result.success();
  }

  private Result validPhone(User user) {
    String phone = user.getPhone();
    Result formatRes = validatePhoneFormat(phone);
    if (!formatRes.isSuccess()) {
      return formatRes;
    }
    if (userMapper.selectOne(new QueryWrapper<User>().eq("phone", phone)) != null) {
      return Result.error("手机号已存在");
    }
    return Result.success();
  }

  // ========== 工具方法 ==========

  private String setSecurePassword(String password) {
    return DigestUtils.md5DigestAsHex((SALT + password).getBytes());
  }
}