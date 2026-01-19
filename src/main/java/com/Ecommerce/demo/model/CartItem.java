package com.Ecommerce.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "cart_items")
public class CartItem {

    @Id
    private String id;

    private String userId;     // FK → USER
    private String productId;  // FK → PRODUCT
    private Integer quantity;
}

