package com.mikael.eCommerce.orders.DTOs;

import com.mikael.eCommerce.orderItems.OrderItemRequestDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponseDTO(Long id, Instant placedAt, List<OrderItemRequestDTO> orderItems, String orderStatus, String address, BigDecimal amount) {
}
