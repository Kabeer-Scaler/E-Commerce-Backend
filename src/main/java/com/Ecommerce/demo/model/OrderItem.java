package com.Ecommerce.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "order_items")
public class OrderItem {

    @Id
    private String id;

    private String orderId;    // FK → ORDER
    private String productId;  // FK → PRODUCT
    private Integer quantity;
    private Double price;      // price at time of order
}

