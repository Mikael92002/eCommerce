package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import com.mikael.eCommerce.users.UserEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/my-orders")
    public List<OrderResponseDTO> getOrderByUsername(@AuthenticationPrincipal UserEntity user){
        return this.orderService.getOrdersByUsername(user);
    }

    @PostMapping("/create")
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO, @AuthenticationPrincipal UserEntity user){
        return this.orderService.createOrder(orderRequestDTO, user);
    }
}
