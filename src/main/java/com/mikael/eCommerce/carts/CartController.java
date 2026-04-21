package com.mikael.eCommerce.carts;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping({"/{userId}"})
    public List<CartDTO> getCart(@PathVariable Long userId) {
        return this.cartService.findByUser_Id(userId);
    }

    @PostMapping("/{userId}/{productId}")
    public CartDTO createCart(@PathVariable Long userId, @PathVariable Long productId, @RequestBody CartDTO cartDTO) {
        return this.cartService.createCart(cartDTO, userId, productId);
    }

    @PutMapping("/{userId}/{productId}/{newQuantity}")
    public CartDTO updateCartQuantity(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int newQuantity) {
        return this.cartService.updateCartQuantity(newQuantity, userId, productId);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        this.cartService.deleteByUser_Id(userId);
        return ResponseEntity.noContent().build();
    }

}
