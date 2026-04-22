package com.mikael.eCommerce.products;

import java.math.BigDecimal;

public record ProductDTO(Long id, String name, BigDecimal price, Integer stockQuantity) {

}
