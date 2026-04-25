package com.mikael.eCommerce.orders;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mikael.eCommerce.orderItems.OrderItemEntity;
import com.mikael.eCommerce.payments.PaymentEntity;
import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.users.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant placedAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", nullable = false)
    private PaymentEntity payment;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String orderStatus; // "SHIPPED", "PENDING", "CANCELLED", "DELIVERED"

    @Column(nullable = false)
    private String address;

    public OrderEntity(){

    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Instant getPlacedAt() {
        return placedAt;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }
}
