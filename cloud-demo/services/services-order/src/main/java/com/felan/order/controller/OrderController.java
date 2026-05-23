package com.felan.order.controller;

import com.felan.order.bean.Order;
import com.felan.order.properties.OrderProperties;
import com.felan.order.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/order")
public class OrderController {
  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderProperties orderProperties;

  @PostMapping("/create")
  public Order createOrder(@RequestParam("productIdList") List<Long> productIdList,
      @RequestParam("userId") Long userId) {
    return orderService.createOrder(productIdList, userId);
  }

  @GetMapping("/config")
  public String getNacosConfig() {
    return "OrderTimeout=" + orderProperties.getTimeout() +
        ",autoConfirm=" + orderProperties.getAutoConfirm() +
        ",database=" + orderProperties.getDatabase();
  }
}
