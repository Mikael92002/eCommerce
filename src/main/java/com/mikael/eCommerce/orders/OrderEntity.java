package com.mikael.eCommerce.orders;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mikael.eCommerce.orderItems.OrderItemEntity;
import com.mikael.eCommerce.payments.PaymentEntity;
import com.mikael.eCommerce.products.ProductEntity;
import com.mikael.eCommerce.users.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @NotNull(message = "placedAt cannot be null")
    private Instant placedAt;

    @PrePersist
    protected void onCreate(){
        this.placedAt = Instant.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "user cannot be null")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @NotNull(message = "orderItem list cannot be null")
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", nullable = false)
    @NotNull(message = "payment cannot be null")
    private PaymentEntity payment;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "amount cannot be null")
    private BigDecimal amount;

    @Column(nullable = false)
    @NotNull(message = "orderStatus cannot be null")
    private String orderStatus; // "SHIPPED", "PENDING", "CANCELLED", "DELIVERED"

    @Column(nullable = false)
    @NotNull(message = "address cannot be null")
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
