package com.example.orderservice.repository;

import com.example.orderservice.model.OrderQueue;
import com.example.orderservice.model.QueueStatus;
import com.example.orderservice.repository.jpa.projection.CountOrderQueueProjection;

public interface QueueRepository {

  CountOrderQueueProjection findBestAvailableQueueId();

  void saveQueue(Long aLong, OrderQueue availableQueue);

  void updateQueueItemStatus(Long orderId, QueueStatus queueStatus);
}
