package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orderItems.OrderItemMapper;
import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={OrderItemMapper.class})
public interface OrderMapper{
    OrderResponseDTO toResponseDTO(OrderEntity order);
    @Mapping(target = "orderItems", ignore = true)
    OrderEntity toEntity(OrderRequestDTO orderRequestDTO);
}
