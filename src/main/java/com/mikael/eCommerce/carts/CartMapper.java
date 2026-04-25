package com.mikael.eCommerce.carts;

import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.users.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    public CartDTO toDTO(CartEntity cart) {
        return new CartDTO(cart.getId(), cart.getQuantity(), cart.getProduct().getName());
    }

    public CartEntity toEntity(CartDTO cartDTO) {
        CartEntity cartEntity = new CartEntity();
        cartEntity.setQuantity(cartDTO.quantity());
        return cartEntity;
    }
}
