package com.felan.order.service.impl;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.felan.order.feign.ProductFeignClient;
import com.felan.product.bean.Product;
import java.math.BigDecimal;

import com.felan.order.bean.Order;
import com.felan.order.service.OrderService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;

@EnableDiscoveryClient
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

  @Autowired
  private ProductFeignClient productFeignClient;

  @SentinelResource(value = "createOrder", blockHandler = "createOrderFallback")
  @Override
  public Order createOrder(List<Long> productIdList, Long userId) {
    Order order = new Order();
    order.setOrderId(100001L);
    order.setProductIdList(productIdList);
    // 从远程Product服务获取订单总价
    order.setTotalPrice(calculateTotalPrice(productIdList));
    order.setUserId(userId);
    order.setUsername("Mary");
    order.setAddress("深圳市");
    return order;
  }

  public Order createOrderFallback(List<Long> productIdList, Long userId, BlockException ex) {
    Order order = new Order();
    order.setOrderId(0L);
    order.setTotalPrice(new BigDecimal(0));
    order.setUserId(userId);
    order.setUsername("未知用户");
    order.setAddress(ex.getClass().toString());
    return order;
  }
    /**
   * 从远程 Product 服务获取订单信息
   * @param productId
   * @return
   */
  private Product getProductFromRemote(Long productId) {
    // 使用openfeign调用远程服务获取产品信息，不需要提供远程服务的url
    Product product = productFeignClient.getProduct(productId);
    return product;
  }

  private BigDecimal calculateTotalPrice(List<Long> productIdList) {
    BigDecimal totalPrice = new BigDecimal("0");
    for (Long productId : productIdList) {
      Product product = getProductFromRemote(productId);
      // 从远程服务获取的产品信息中提取价格，并累加到 totalPrice 中
      // 这里假设从远程服务获取的产品信息是一个包含 price 字段的对象
      BigDecimal price = BigDecimal.valueOf(product.getPrice());
      totalPrice = totalPrice.add(price);
    }
    return totalPrice;
  }
}
