package com.Ecommerce.demo.service;

import com.Ecommerce.demo.model.*;
import com.Ecommerce.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public Order createOrderFromCart(String userId) {

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double totalAmount = 0;

        // Validate stock & calculate total
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            totalAmount += product.getPrice() * item.getQuantity();
        }

        // Create Order
        Order order = Order.builder()
                .userId(userId)
                .totalAmount(totalAmount)
                .status("CREATED")
                .createdAt(Instant.now())
                .build();

        Order savedOrder = orderRepository.save(order);

        // Create OrderItems and reduce stock
        for (CartItem item : cartItems) {
            Product product = productRepository.findById(item.getProductId()).get();

            OrderItem orderItem = OrderItem.builder()
                    .orderId(savedOrder.getId())
                    .productId(product.getId())
                    .quantity(item.getQuantity())
                    .price(product.getPrice())
                    .build();

            orderItemRepository.save(orderItem);

            // Reduce stock
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
        }

        // Clear cart
        cartItemRepository.deleteByUserId(userId);

        return savedOrder;
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<OrderItem> getOrderItems(String orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public List<Order> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order cancelOrder(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if ("PAID".equals(order.getStatus())) {
            throw new RuntimeException("Cannot cancel a PAID order");
        }

        List<OrderItem> items = orderItemRepository.findByOrderId(orderId);

        // Restore stock
        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProductId()).get();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }


}

