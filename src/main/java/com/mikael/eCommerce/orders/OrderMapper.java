package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orderItems.OrderItemMapper;
import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={OrderItemMapper.class})
public interface OrderMapper{
    OrderRequestDTO toDTO(OrderEntity order);
    OrderEntity toEntity(OrderRequestDTO orderRequestDTO);
}
