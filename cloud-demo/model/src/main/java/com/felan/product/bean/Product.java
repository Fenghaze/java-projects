package com.felan.product.bean;

import lombok.Data;

@Data
public class Product {
    private long productId;
    private String name;
    private Double price;
}
