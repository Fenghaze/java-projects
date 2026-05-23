package com.felan.order.bean;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class Order {
  private Long orderId;
  private List<Long> productIdList;
  private BigDecimal totalPrice;
  private Long userId;
  private String username;
  private String address;
}
