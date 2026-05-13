package com.mikael.eCommerce.orderItems;

import com.mikael.eCommerce.products.DTOs.ProductResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItemDTO toDTO(OrderItemEntity orderItem) {
        ProductResponseDTO productResponseDTO = new ProductResponseDTO(orderItem.getProduct().getId(), orderItem.getProduct().getName(), orderItem.getProduct().getPrice(), orderItem.getProduct().getStockQuantity());
        return new OrderItemDTO(orderItem.getId(), productResponseDTO, orderItem.getQuantity(), orderItem.getPrice());
    }

    public OrderItemEntity toEntity(OrderItemDTO orderItemDTO){
        // does not touch any other entities
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setPrice(orderItemDTO.price());
        orderItem.setQuantity(orderItemDTO.quantity());
        return orderItem;
    }
}
