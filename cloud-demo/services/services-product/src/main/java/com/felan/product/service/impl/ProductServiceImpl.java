package com.felan.product.service.impl;

import com.felan.product.bean.Product;
import com.felan.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
  @Override
  public Product getProduct(Long productId) {
    Product product = new Product();
    product.setProductId(1);
    product.setName("Apple iPhone 14 Pro Max");
    product.setPrice(10999.00);
    return product;
  }
}
