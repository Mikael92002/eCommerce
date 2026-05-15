package com.mikael.eCommerce.products;

import com.mikael.eCommerce.products.DTOs.ProductRequestDTO;
import com.mikael.eCommerce.products.DTOs.ProductResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper{
    // from backend to front end:
    ProductResponseDTO toResponseDTO(ProductEntity product);
    ProductEntity toEntity(ProductRequestDTO dto);
    void updateEntityFromDTO(ProductRequestDTO dto, @MappingTarget ProductEntity entity);
}
