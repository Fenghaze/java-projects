package com.felan.order.feign;

import com.felan.order.feign.fallback.ProductFeignClientFallback;
import com.felan.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "services-product", fallback= ProductFeignClientFallback.class)
public interface ProductFeignClient {
  @GetMapping("/api/product/{id}") // 向services-product服务发送一个get请求
  public Product getProduct(@PathVariable("id") Long productId);
}
