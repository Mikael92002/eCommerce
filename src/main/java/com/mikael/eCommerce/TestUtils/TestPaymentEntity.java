package com.mikael.eCommerce.TestUtils;

import com.mikael.eCommerce.enums.CurrencyEnum;
import com.mikael.eCommerce.enums.PaymentStatusEnum;
import com.mikael.eCommerce.orders.OrderEntity;
import com.mikael.eCommerce.payments.PaymentEntity;
import com.mikael.eCommerce.users.UserEntity;

import java.math.BigDecimal;

public class TestPaymentEntity {

    public TestPaymentEntity(){

    }

    public static PaymentEntity fakePayment(OrderEntity order, UserEntity user){
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(new BigDecimal(100));
        payment.setCurrency(CurrencyEnum.$);
        payment.setStatus(PaymentStatusEnum.PENDING);
        payment.setOrder(order);
        payment.setUser(user);
        payment.setExternalTransactionId("sk_test_123");

        return payment;
    }
}
