package com.example.orderservice.repository.impl;

import com.example.orderservice.model.OrderQueue;
import com.example.orderservice.model.QueueStatus;
import com.example.orderservice.repository.QueueRepository;
import com.example.orderservice.repository.jpa.JpaOrderQueueRepository;
import com.example.orderservice.repository.jpa.projection.CountOrderQueueProjection;
import com.example.orderservice.service.mapper.QueueMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueRepositoryImpl implements QueueRepository {

  public static final int FIRST_PAGE = 0;
  public static final int FIRST_ITEM = 1;

  private final JpaOrderQueueRepository jpaOrderQueueRepository;

  private final QueueMapper queueMapper;

  @Override
  public CountOrderQueueProjection findBestAvailableQueueId() {
    val pageRequest = PageRequest.of(FIRST_PAGE, FIRST_ITEM);
    return jpaOrderQueueRepository.findBestAvailableQueueId(pageRequest)
        .getContent()
        .stream()
        .findFirst().orElse(null);
  }

  @Override
  public void saveQueue(Long orderId, OrderQueue availableQueue) {
    queueMapper.mapToJpaOrderQueue(orderId, availableQueue);
  }

  @Override
  public void updateQueueItemStatus(Long orderId, QueueStatus queueStatus) {
    jpaOrderQueueRepository.updateQueueItemStatus(orderId, queueStatus);
  }
}
