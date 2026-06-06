package com.felan.usercenter;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.felan.usercenter.mapper.UserMapper;
import com.felan.usercenter.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybatisPlusTest {
  @Autowired
  private UserMapper userMapper;
  @Test
  public void testSelect() {
    System.out.println(("----- selectAll method test ------"));
    List<User> userList = userMapper.selectList(null);
    Assert.isTrue(5 == userList.size(), "");
    userList.forEach(System.out::println);
  }
}
