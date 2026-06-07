package com.felan.usercenter.service;

import com.felan.usercenter.model.User;
import com.felan.usercenter.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class UserServiceTest {
  @Autowired
  private UserService userService;

  @Test
  @Transactional
  @Rollback
  public void register() {
    User user = new User();
    user.setAccount("Felan");
    user.setUsername("Felan");
    user.setPassword("12345678");
    user.setEmail("feng_haze@163.com");
    user.setPhone("18783676920");
    Result res = userService.register(user);
    assertTrue(res.isSuccess(), res.getMsg());
  }

  @Test
  void loginByAccount_fail_whenPasswordTooShort() {
    User user = new User();
    user.setAccount("Felan122");
    user.setPassword("123456");
    Result res = userService.login(user);
    assertFalse(res.isSuccess());
  }

  @Test
  void loginByAccount() {
    User user = new User();
    user.setAccount("Felan122");
    user.setPassword("12345678");
    Result res = userService.login(user);
    assertTrue(res.isSuccess(), res.getMsg());
  }

  @Test
  void loginByPhone() {
    User user = new User();
    user.setPhone("18783676921");
    user.setPassword("12345678");
    Result res = userService.login(user);
    assertTrue(res.isSuccess(), res.getMsg());
  }

  @Test
  void loginByEmail() {
    User user = new User();
    user.setEmail("qwe@163.com");
    user.setPassword("12345678");
    Result res = userService.login(user);
    assertTrue(res.isSuccess(), res.getMsg());
  }

  @Test
  void loginByAccount2() {
    User user = new User();
    user.setAccount("Felan");
    user.setPassword("12345678");
    Result res = userService.login(user);
    assertTrue(res.isSuccess(), res.getMsg());
  }
}
