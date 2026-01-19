package com.Ecommerce.demo.service;

import com.Ecommerce.demo.model.CartItem;
import com.Ecommerce.demo.model.Product;
import com.Ecommerce.demo.repository.CartItemRepository;
import com.Ecommerce.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartItem addToCart(String userId, String productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available");
        }

        return cartItemRepository.findByUserIdAndProductId(userId, productId)
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    return cartItemRepository.save(existingItem);
                })
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .userId(userId)
                            .productId(productId)
                            .quantity(quantity)
                            .build();
                    return cartItemRepository.save(newItem);
                });
    }

    public List<CartItem> getUserCart(String userId) {
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        cartItemRepository.deleteByUserId(userId);
    }
}
