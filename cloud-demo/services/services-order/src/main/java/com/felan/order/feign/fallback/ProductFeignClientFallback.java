package com.felan.order.feign.fallback;

import com.felan.order.feign.ProductFeignClient;
import com.felan.product.bean.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {
  @Override
  public Product getProduct(Long productId) {
    // 当调用services-product服务失败时，返回一个默认的Product对象
    Product product = new Product();
    product.setProductId(0L);
    product.setName("默认商品");
    product.setPrice(0.0D);
    return product;
  }
}
