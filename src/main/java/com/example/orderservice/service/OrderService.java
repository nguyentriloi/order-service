package com.example.orderservice.service;

import com.example.orderservice.message.request.CreateOrderRequest;
import com.example.orderservice.message.request.UpdatePaymentEvent;

public interface OrderService {

  Long createOrder(CreateOrderRequest request);

  void updateOrderPayment(UpdatePaymentEvent event);

  void readyForPickup(Long id);

  void pickup(Long id);

  void complete(Long id);
}
