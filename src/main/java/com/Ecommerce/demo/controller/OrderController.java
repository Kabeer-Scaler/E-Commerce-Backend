package com.Ecommerce.demo.controller;

import com.Ecommerce.demo.dto.CreateOrderRequest;
import com.Ecommerce.demo.model.Order;
import com.Ecommerce.demo.model.OrderItem;
import com.Ecommerce.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody CreateOrderRequest request) {
        return orderService.createOrderFromCart(request.getUserId());
    }


    @GetMapping("/{orderId}")
    public Map<String, Object> getOrderDetails(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        List<OrderItem> items = orderService.getOrderItems(orderId);

        Map<String, Object> response = new HashMap<>();
        response.put("order", order);
        response.put("items", items);

        return response;
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable String userId) {
        return orderService.getOrdersByUser(userId);
    }

    @PostMapping("/{orderId}/cancel")
    public Order cancelOrder(@PathVariable String orderId) {
        return orderService.cancelOrder(orderId);
    }


}

