package com.felan.order.service.impl;
import com.felan.product.bean.Product;
import java.math.BigDecimal;

import com.felan.order.bean.Order;
import com.felan.order.service.OrderService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
  @Autowired
  private DiscoveryClient discoveryClient;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private LoadBalancerClient loadBalancerClient;

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

  /**
   * 从远程 Product 服务获取订单信息
   * @param productId
   * @return
   */
  private Product getProductFromRemote(Long productId) {
    // 使用 discoveryClient 获取远程 Product 服务的实例
    List<ServiceInstance> instances = discoveryClient.getInstances("services-product");
    if (instances == null || instances.isEmpty()) {
      throw new RuntimeException("没有找到 services-product 服务实例");
    }
    // 然后通过负载均衡随机选择一个远程服务地址，用于获取产品信息
    // String productServiceUrl = loadBalancerClient.choose("services-product").getUri().toString() + "/api/product/" + productId;
    String productServiceUrl = "http://services-product" + "/api/product/" + productId;
    log.info("send request to :{}", productServiceUrl);
    // 这里可以使用 RestTemplate 或 WebClient 来调用远程服务获取产品信息
    Product product = restTemplate.getForObject(productServiceUrl, Product.class);
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
