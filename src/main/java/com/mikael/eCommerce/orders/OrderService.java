package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderRequestDTO> getOrdersByUser_Id(Long id){
        List<OrderEntity> orders = this.orderRepository.findByUser_Id(id);
        List<OrderRequestDTO> orderRequestDTOS = orders.stream().map(order->this.orderMapper.toDTO(order)).toList();

        return orderRequestDTOS;
    }


}
