package com.felan.order;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@SpringBootTest
public class DiscoveryTest {
  @Autowired
  DiscoveryClient discoveryClient;

  @Test
  void discoveryClientTest() {
    System.out.println("Nacos服务发现");
    for (String serviceName : discoveryClient.getServices()) {
      System.out.println(serviceName);
      List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
      for (ServiceInstance instance : instances) {
        System.out.println(instance.getUri());
      }
    }
  }
}
