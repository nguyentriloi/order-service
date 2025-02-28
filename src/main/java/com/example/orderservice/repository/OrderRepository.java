package com.example.orderservice.repository;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;

public interface OrderRepository {

  Long save(Order order);

  void updateOrderStatusById(Long orderId, OrderStatus orderStatus);

  void updateOrderStatusByIdAndCurrentStatus(Long orderId,
      OrderStatus orderStatus, OrderStatus currentStatus);
}
