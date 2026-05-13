package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/my-orders")
    public List<OrderResponseDTO> getOrderByUserId(@AuthenticationPrincipal UserDetails userDetails){
        return this.orderService.getOrdersByUsername(userDetails.getUsername());
    }
}
