package com.example.orderservice.service.mapper;

import com.example.orderservice.message.request.CreateOrderRequest;
import com.example.orderservice.message.response.CreateOrderResponse;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.jpa.entity.JpaOrder;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

  private final OrderItemMapper orderItemMapper;

  public Order createOrder(CreateOrderRequest request) {
    if (request == null) {
      return null;
    }

    return Order.builder()
        .customerId(request.getCustomerId())
        .status(OrderStatus.PENDING)
        .totalAmount(request.getTotalAmount())
        .orderDate(LocalDateTime.now())
        .orderItems(orderItemMapper.createOrderItems(request.getItems()))
        .build();
  }

  public CreateOrderResponse mapToCreateOrderResponse(JpaOrder savedOrder) {
    return CreateOrderResponse.builder()
        .customerId(savedOrder.getCustomerId())
        .status(savedOrder.getStatus())
        .build();
  }

  public JpaOrder mapToJpaOrder(Order order) {
    if (order == null) {
      return null;
    }

    return JpaOrder.builder()
        .customerId(order.getCustomerId())
        .status(order.getStatus())
        .orderDate(LocalDateTime.now())
        .totalAmount(order.getTotalAmount())
        .build();
  }


}
