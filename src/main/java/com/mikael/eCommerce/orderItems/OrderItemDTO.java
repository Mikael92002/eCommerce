package com.mikael.eCommerce.orderItems;

import com.mikael.eCommerce.products.DTOs.ProductRequestDTO;
import com.mikael.eCommerce.products.DTOs.ProductResponseDTO;

import java.math.BigDecimal;

public record OrderItemDTO(Long id, ProductRequestDTO product, Integer quantity, BigDecimal price) {
}
