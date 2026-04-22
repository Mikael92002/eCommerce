package com.mikael.eCommerce.products;

public class ProductMapper {
    public ProductDTO toDTO(ProductEntity product){
        return new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getStockQuantity());
    }

    public ProductEntity toEntity(ProductDTO productDTO){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productEntity.getId());
        productEntity.setName(productDTO.name());
        productEntity.setPrice(productDTO.price());
        productEntity.setStockQuantity(productDTO.stockQuantity());
        return productEntity;
    }
}
