package com.Ecommerce.demo.dto;

import lombok.Data;

@Data
public class AddtoCartRequest {
    private String userId;
    private String productId;
    private Integer quantity;
}

