package com.example.orderservice.service.mapper;

import com.example.orderservice.model.OrderQueue;
import com.example.orderservice.model.QueueStatus;
import com.example.orderservice.repository.jpa.entity.JpaOrderQueue;
import org.springframework.stereotype.Component;

@Component
public class QueueMapper {

  public JpaOrderQueue mapToJpaOrderQueue(Long orderId, OrderQueue orderQueue) {
    if (orderQueue == null) {
      return null;
    }

    return JpaOrderQueue.builder()
        .queueId(orderQueue.getQueueId())
        .orderId(orderId)
        .estimatedReadyTime(orderQueue.getEstimatedReadyTime())
        .status(QueueStatus.PREPARING)
        .build();
  }
}
