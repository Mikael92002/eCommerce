package com.mikael.eCommerce.orderItems;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "product.id", target = "productId")
    OrderItemRequestDTO toDTO(OrderItemEntity orderItem);
}