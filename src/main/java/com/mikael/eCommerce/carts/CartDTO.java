package com.mikael.eCommerce.carts;

public record CartDTO(Integer quantity, String productName) {

    public Integer getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
