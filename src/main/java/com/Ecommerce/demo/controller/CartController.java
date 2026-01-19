package com.Ecommerce.demo.controller;

import com.Ecommerce.demo.dto.AddtoCartRequest;
import com.Ecommerce.demo.model.CartItem;
import com.Ecommerce.demo.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public CartItem addToCart(@RequestBody AddtoCartRequest request) {
        return cartService.addToCart(
                request.getUserId(),
                request.getProductId(),
                request.getQuantity()
        );
    }


    @GetMapping("/{userId}")
    public List<CartItem> getUserCart(@PathVariable String userId) {
        return cartService.getUserCart(userId);
    }

    @DeleteMapping("/{userId}/clear")
    public String clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
        return "Cart cleared successfully";
    }
}

