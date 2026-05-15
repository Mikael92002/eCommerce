package com.mikael.eCommerce.payments;

import com.mikael.eCommerce.enums.CurrencyEnum;
import com.mikael.eCommerce.enums.PaymentStatusEnum;
import com.mikael.eCommerce.orders.OrderEntity;
import com.mikael.eCommerce.users.UserEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private CurrencyEnum currency;

    @Column(nullable = false)
    private PaymentStatusEnum status; //"PENDING", "COMPLETED", "REFUNDED"

    @Column(unique = true)
    private String externalTransactionId; // comes from Stripe

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(mappedBy = "payment")
    private OrderEntity order;

    public PaymentEntity(){

    }

    public UserEntity getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }
}
