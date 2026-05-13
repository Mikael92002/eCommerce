package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orderItems.OrderItemMapper;
import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={OrderItemMapper.class})
public interface OrderMapper{
    OrderResponseDTO toDTO(OrderEntity order);
    OrderEntity toEntity(OrderRequestDTO orderRequestDTO);
}
