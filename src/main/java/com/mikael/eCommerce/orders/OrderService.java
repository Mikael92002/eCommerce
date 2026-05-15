package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.TestUtils.TestPaymentEntity;
import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import com.mikael.eCommerce.payments.PaymentEntity;
import com.mikael.eCommerce.users.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
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

    public List<OrderResponseDTO> getOrdersByUsername(UserEntity user){
        // should never be hit:
        if(user == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Must be logged in to view orders");
        }
        List<OrderEntity> orders = this.orderRepository.findByUser_Username(user.getUsername());
        List<OrderResponseDTO> orderResponseDTOs = orders.stream().map(order->this.orderMapper.toResponseDTO(order)).toList();

        return orderResponseDTOs;
    }

    @PreAuthorize("hasAuthority('user')")
    public void deleteOrderById(Long id){
        if(!this.orderRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DELETE FAILED: Order does not exist");
        }
        this.orderRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, UserEntity user){
        // address, amount, orderStatus, orderItems set by mapper:
        OrderEntity orderEntity = this.orderMapper.toEntity(orderRequestDTO);

        // set entities user, payment manually:
        orderEntity.setUser(user);
        // FAKE for now, but should come from front end (?):
        PaymentEntity paymentEntity = TestPaymentEntity.fakePayment(orderEntity, user);
        // should amount come from front end too or payment/product (for now front end)?
        orderEntity.setPayment(paymentEntity);

        OrderEntity savedOrder = this.orderRepository.save(orderEntity);

        return this.orderMapper.toResponseDTO(savedOrder);
    }
}
