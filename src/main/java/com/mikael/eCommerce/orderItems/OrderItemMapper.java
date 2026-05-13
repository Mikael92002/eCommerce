package com.mikael.eCommerce.orderItems;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper{
    OrderItemDTO toDTO(OrderItemEntity orderItem);
    @Mapping(target="order", ignore=true)
    @Mapping(target="id", ignore=true)
    OrderItemEntity toEntity(OrderItemDTO orderItemDTO);
}