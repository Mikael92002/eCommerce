package com.mikael.eCommerce.orders;


import com.mikael.eCommerce.orderItems.OrderItemDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderDTO(Long id, Instant placedAt, List<OrderItemDTO> orderItemDTOList, String orderStatus, String address, BigDecimal amount) {

}
