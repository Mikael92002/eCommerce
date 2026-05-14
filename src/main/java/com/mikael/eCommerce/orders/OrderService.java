package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderResponseDTO> getOrdersByUsername(String username){
        List<OrderEntity> orders = this.orderRepository.findByUser_Username(username);
        List<OrderResponseDTO> orderResponseDTOs = orders.stream().map(order->this.orderMapper.toDTO(order)).toList();

        return orderResponseDTOs;
    }

    @PreAuthorize("hasAuthority('user')")
    public void deleteOrderById(Long id){
        if(!this.orderRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DELETE FAILED: Order does not exist");
        }
        this.orderRepository.deleteById(id);
    }
}
