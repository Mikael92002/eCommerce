package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orderItems.OrderItemDTO;
import com.mikael.eCommerce.orderItems.OrderItemMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public OrderDTO toDTO(OrderEntity order) {
        List<OrderItemDTO> orderItems = order.getOrderItems().stream().map(orderItem -> this.orderItemMapper.toDTO(orderItem)).toList();
        return new OrderDTO(order.getId(), order.getPlacedAt(), orderItems, order.getOrderStatus(), order.getAddress(), order.getAmount());
    }

    public OrderEntity toEntity(OrderDTO orderDTO) {
        // does not touch user, payment, orderItem entities:
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderStatus(orderDTO.orderStatus());
        orderEntity.setAddress(orderDTO.address());
        orderEntity.setAmount(orderDTO.amount());
        return orderEntity;
    }

}
