package com.mikael.eCommerce.orders.DTOs;

import com.mikael.eCommerce.orderItems.OrderItemDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDTO(Long id, Instant placedAt, List<OrderItemDTO> orderItems, String orderStatus, String address, BigDecimal amount) {
}
