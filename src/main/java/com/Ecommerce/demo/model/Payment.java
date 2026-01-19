package com.Ecommerce.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "payments")
public class Payment {

    @Id
    private String id;

    private String orderId;     // FK → ORDER
    private Double amount;
    private String status;      // PENDING, SUCCESS, FAILED
    private String paymentId;   // external or mock payment id
    private Instant createdAt;
}

