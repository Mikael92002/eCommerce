package com.mikael.eCommerce.orders.DTOs;


import com.mikael.eCommerce.orderItems.OrderItemDTO;

import java.math.BigDecimal;
import java.util.List;

// from front end to back end:
public record OrderRequestDTO(List<OrderItemDTO> orderItems, String orderStatus, String address, BigDecimal amount) {

}
