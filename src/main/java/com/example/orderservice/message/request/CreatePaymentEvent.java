package com.example.orderservice.message.request;

import com.example.orderservice.model.PaymentMethod;
import java.math.BigDecimal;

public record CreatePaymentEvent(Long orderId, PaymentMethod paymentMethod, BigDecimal amount) {

}
