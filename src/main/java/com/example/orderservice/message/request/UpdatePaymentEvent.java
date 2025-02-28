package com.example.orderservice.message.request;

import com.example.orderservice.model.PaymentMethod;
import com.example.orderservice.model.PaymentStatus;

public record UpdatePaymentEvent(Long orderId,
                                 PaymentMethod paymentMethod,
                                 PaymentStatus status) {
}
