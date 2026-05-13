package com.mikael.eCommerce.products.DTOs;

import java.math.BigDecimal;

// from backend to frontend (id column = use in React id column
// and url (to send update response to backend))
public record ProductResponseDTO(Long id, String name, BigDecimal price, Integer stockQuantity) {
}