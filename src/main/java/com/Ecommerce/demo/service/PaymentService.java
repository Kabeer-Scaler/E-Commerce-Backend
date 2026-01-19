package com.Ecommerce.demo.service;

import com.Ecommerce.demo.model.Order;
import com.Ecommerce.demo.model.Payment;
import com.Ecommerce.demo.repository.OrderRepository;
import com.Ecommerce.demo.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final RazorpayClient razorpayClient;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public Payment createPayment(String orderId) throws Exception {

        // 1. Validate order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getStatus().equals("CREATED")) {
            throw new RuntimeException("Only CREATED orders can be paid");
        }

        // 2. Create Razorpay Order
        JSONObject options = new JSONObject();
        options.put("amount", order.getTotalAmount() * 100); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", orderId);

        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

        // 3. Save Payment in DB
        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(order.getTotalAmount())
                .status("PENDING")
                .paymentId(razorpayOrder.get("id"))
                .createdAt(Instant.now())
                .build();

        return paymentRepository.save(payment);
    }
}
