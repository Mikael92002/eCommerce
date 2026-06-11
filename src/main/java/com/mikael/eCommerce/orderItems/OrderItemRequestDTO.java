package com.mikael.eCommerce.orderItems;

import jakarta.validation.constraints.NotNull;

public record OrderItemRequestDTO(@NotNull(message = "productId cannot be null") Long productId, @NotNull(message = "quantity cannot be null") Integer quantity) {
}
