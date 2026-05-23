package com.felan.product.controller;

import com.felan.product.bean.Product;
import com.felan.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Autowired
  private ProductService productService;

  // 根据商品id查询商品
  @GetMapping("/{id}")
  public Product getProduct(@PathVariable("id") Long productId) {
    return productService.getProduct(productId);
  }

}
