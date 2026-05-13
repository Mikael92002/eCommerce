package com.mikael.eCommerce.products.DTOs;

import java.math.BigDecimal;

// from front end to back end: (No id, use url PathVariable for id)
public record ProductRequestDTO(String name, BigDecimal price, Integer stockQuantity) {
}