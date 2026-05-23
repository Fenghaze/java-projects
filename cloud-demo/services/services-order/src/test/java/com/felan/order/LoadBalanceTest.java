package com.felan.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@SpringBootTest
public class LoadBalanceTest {
  @Autowired
  LoadBalancerClient loadBalancerClient;

  @Test
  void loadBalancerClientTest() {
    System.out.println("Nacos负载均衡");
    for (int i = 0; i < 5; i++) {
      System.out.println(loadBalancerClient.choose("services-product").getUri());
    }
  }
}
