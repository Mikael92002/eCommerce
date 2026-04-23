package com.mikael.eCommerce.orderItems;

import com.mikael.eCommerce.orders.OrderEntity;
import com.mikael.eCommerce.products.ProductEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

// Called a "Join Entity" (similar to cartEntity)

// This entity needs to be created because of the
// following reasons:
// 1. In the alternative of linking OrderEntity
// and ProductEntity with a many-to-many relationship
// there is no way to get: quantity/price of items bought
// 2. primary keys from above many-to-many is
// created with composite of order_id and
// product_id, meaning same product cannot be
// bought more than once

@Entity
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_entity_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public OrderItemEntity(){

    }
}
