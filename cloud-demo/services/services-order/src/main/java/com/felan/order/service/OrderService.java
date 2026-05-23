package com.felan.order.service;

import com.felan.order.bean.Order;
import java.util.List;

public interface OrderService {
  public Order createOrder(List<Long> productIdList, Long userId);
}
