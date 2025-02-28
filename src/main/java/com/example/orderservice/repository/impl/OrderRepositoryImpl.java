package com.example.orderservice.repository.impl;

import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.jpa.JpaOrderItemRepository;
import com.example.orderservice.repository.jpa.JpaOrderQueueRepository;
import com.example.orderservice.repository.jpa.JpaOrderRepository;
import com.example.orderservice.service.mapper.OrderItemMapper;
import com.example.orderservice.service.mapper.OrderMapper;
import com.example.orderservice.service.mapper.QueueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;

  private final JpaOrderItemRepository jpaOrderItemRepository;

  private final JpaOrderQueueRepository jpaOrderQueueRepository;

  private final OrderMapper orderMapper;

  private final OrderItemMapper orderItemMapper;

  private final QueueMapper queueMapper;

  @Override
  @Transactional
  public Long save(Order order) {
    log.info("Starting save order of customer {}", order.getCustomerId());
    val jpaOrder = orderMapper.mapToJpaOrder(order);
    val savedOrder = jpaOrderRepository.saveAndFlush(jpaOrder);
    val orderId = savedOrder.getId();
    if (CollectionUtils.isNotEmpty(order.getOrderItems())) {
      saveOrderItems(order, orderId);
    }

    if (order.getOrderQueue() != null) {
      saveOrderQueue(order, orderId);
    }
    log.info("Saved order of customer {}", order.getCustomerId());
    return savedOrder.getId();
  }

  @Override
  public void updateOrderStatusById(Long orderId, OrderStatus orderStatus) {
    jpaOrderRepository.updateOrderStatusByOrderId(orderId, orderStatus);
  }

  @Override
  public void updateOrderStatusByIdAndCurrentStatus(Long orderId, OrderStatus orderStatus,
      OrderStatus currentStatus) {
    jpaOrderRepository.updateOrderStatusByIdAndCurrentStatus(orderId, orderStatus, currentStatus);
  }

  private void saveOrderQueue(Order order, Long orderId) {
    val jpaOrderQueue = queueMapper.mapToJpaOrderQueue(orderId, order.getOrderQueue());
    jpaOrderQueueRepository.save(jpaOrderQueue);
  }

  private void saveOrderItems(Order order, Long orderId) {
    val jpaOrderItems = orderItemMapper.mapToJpaOrderItems(
        orderId, order.getOrderItems());
    jpaOrderItemRepository.saveAll(jpaOrderItems);
  }
}
