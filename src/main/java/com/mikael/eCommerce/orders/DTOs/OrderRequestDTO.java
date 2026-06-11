package com.mikael.eCommerce.orders.DTOs;


import com.mikael.eCommerce.orderItems.OrderItemRequestDTO;
import jakarta.validation.Valid;

import java.util.List;

// from front end to back end:
// SHOULD INCLUDE PAYMENT EVENTUALLY:
public record OrderRequestDTO(@Valid List<OrderItemRequestDTO> orderItems, String orderStatus, String address) {

}
