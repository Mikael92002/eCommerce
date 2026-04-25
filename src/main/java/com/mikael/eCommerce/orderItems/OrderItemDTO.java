package com.mikael.eCommerce.orderItems;

import com.mikael.eCommerce.products.ProductDTO;

import java.math.BigDecimal;

public record OrderItemDTO(Long id, ProductDTO product, Integer quantity, BigDecimal price) {
}
