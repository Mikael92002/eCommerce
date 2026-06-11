package com.mikael.eCommerce.orders;

import com.mikael.eCommerce.TestUtils.TestPaymentEntity;
import com.mikael.eCommerce.orderItems.OrderItemEntity;
import com.mikael.eCommerce.orderItems.OrderItemMapper;
import com.mikael.eCommerce.orders.DTOs.OrderRequestDTO;
import com.mikael.eCommerce.orders.DTOs.OrderResponseDTO;
import com.mikael.eCommerce.payments.PaymentEntity;
import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.products.ProductRepository;
import com.mikael.eCommerce.users.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, OrderItemMapper orderItemMapper, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productRepository = productRepository;
    }

    public List<OrderResponseDTO> getOrdersByUsername(UserEntity user) {
        // should never be hit:
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Must be logged in to view orders");
        }
        List<OrderEntity> orders = this.orderRepository.findByUser_Username(user.getUsername());
        List<OrderResponseDTO> orderResponseDTOs = orders.stream().map(order -> this.orderMapper.toResponseDTO(order)).toList();

        return orderResponseDTOs;
    }

    @PreAuthorize("hasAuthority('user')")
    public void deleteOrderById(Long id) {
        if (!this.orderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DELETE FAILED: Order does not exist");
        }
        this.orderRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('USER')")
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO, UserEntity user) {
        // address, orderStatus set by mapper:
        OrderEntity orderEntity = this.orderMapper.toEntity(orderRequestDTO);
        // Order entity, inside orderItemEntity NOT set (should be set
        // in cascade automatically).
        // Product IS NOT set with mapper:

        // productId, orderItemEntity map:
        Map<Long, OrderItemEntity> productToOrderItemEntityMap = new HashMap<>();
        // list to iterate over orderItemEntities:
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        // final amount:
        final BigDecimal[] totalAmount = {new BigDecimal(0)};

        orderRequestDTO.orderItems().forEach(orderItem -> {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            productToOrderItemEntityMap.put(orderItem.productId(), orderItemEntity);

            orderItemEntity.setQuantity(orderItem.quantity());

            orderItemEntities.add(orderItemEntity);
        });

        List<ProductEntity> productEntities = productRepository.findAllById(productToOrderItemEntityMap.keySet());
        if(productEntities.size() != productToOrderItemEntityMap.size()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Products with ids not found present in request");
        }

        for(ProductEntity productEntity:productEntities){
            OrderItemEntity orderItemEntity = productToOrderItemEntityMap.get(productEntity.getId());

            if(productEntity.getStockQuantity()<orderItemEntity.getQuantity()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock for following product: " + productEntity.getId());
            }

            orderItemEntity.setProduct(productEntity);

            // note that price represents a single unit cost here.
            orderItemEntity.setPrice(productEntity.getPrice());

            // Total orderItemEntity price can be determined as below:
            BigDecimal totalProductPrice = productEntity.getPrice().multiply(BigDecimal.valueOf(orderItemEntity.getQuantity()));
            totalAmount[0] = totalAmount[0].add(totalProductPrice);

            orderItemEntity.setOrder(orderEntity);
        }

        orderEntity.setOrderItems(orderItemEntities);
        orderEntity.setAmount(totalAmount[0]);

        // set entities user, payment manually:
        orderEntity.setUser(user);
        // FAKE for now, but should come from front end (NO):
        PaymentEntity paymentEntity = TestPaymentEntity.fakePayment(orderEntity, user);
        paymentEntity.setOrder(orderEntity);
        // payment amount should not come from front end (but for now front end):
        orderEntity.setPayment(paymentEntity);

        OrderEntity savedOrder = this.orderRepository.save(orderEntity);

        return this.orderMapper.toResponseDTO(savedOrder);
    }
}
