package com.felan.usercenter.controller;

import com.felan.usercenter.model.User;
import com.felan.usercenter.service.UserService;
import com.felan.usercenter.utils.Result;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class RequestAuthController {

  private static final String USER_SESSION_KEY = "user-center:session:";

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public Result login(@RequestBody User user, HttpServletRequest request) {
    Result res = userService.login(user);
    if (res.isSuccess()) {
      request.getSession().setAttribute(USER_SESSION_KEY + res.getData(), res.getData());
    }
    return res;
  }

  @PostMapping("/register")
  public Result register(@RequestBody User user) {
    return userService.register(user);
  }

}
