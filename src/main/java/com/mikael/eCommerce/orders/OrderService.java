package com.mikael.eCommerce.orders;

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

    public List<OrderDTO> getOrdersByUser_Id(Long id){
        List<OrderEntity> orders = this.orderRepository.findByUser_Id(id);
        List<OrderDTO> orderDTOS = orders.stream().map(order->this.orderMapper.toDTO(order)).toList();

        return orderDTOS;
    }


}
