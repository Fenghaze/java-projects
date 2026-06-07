package com.felan.usercenter.controller;

import com.felan.usercenter.model.User;
import com.felan.usercenter.model.request.UserListRequest;
import com.felan.usercenter.service.UserService;
import com.felan.usercenter.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/manage")
public class RequestManageController {
  @Autowired
  private UserService userService;

  @DeleteMapping("/delete/{id}")
  public Result delete(@PathVariable Long id,
                       @RequestParam Long operatorId) {
    return userService.deleteById(id, operatorId);
  }

  @PostMapping("/list")
  public Result listUsers(@RequestBody UserListRequest request) {
    User user = new User();
    BeanUtils.copyProperties(request, user);
    return userService.listUsers(user, request.getPage(), request.getSize());
  }
}
