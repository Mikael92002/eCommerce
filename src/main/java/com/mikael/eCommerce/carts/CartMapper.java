package com.mikael.eCommerce.carts;

import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.users.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartDTO toDTO(CartEntity cart) {
        return new CartDTO(cart.getQuantity(), cart.getProduct().getName());
    }

    public CartEntity toEntity(CartDTO cartDTO, UserEntity user, ProductEntity product) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setUser(user);
        cartEntity.setProduct(product);
        cartEntity.setQuantity(cartDTO.getQuantity());
        return cartEntity;
    }
}
