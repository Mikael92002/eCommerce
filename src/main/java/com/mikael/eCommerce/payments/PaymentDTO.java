package com.mikael.eCommerce.payments;

import java.math.BigDecimal;

// get last 4 digits from stripe API
public record PaymentDTO(Long id, BigDecimal amount, String currency, String status, String lastFourDigits) {
}
