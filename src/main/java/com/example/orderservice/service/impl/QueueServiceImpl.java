package com.example.orderservice.service.impl;

import com.example.orderservice.repository.QueueRepository;
import com.example.orderservice.service.QueueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

  private final QueueRepository queueRepository;

  @Override
  public Pair<Integer, Long> findBestQueueId() {
    val result = queueRepository.findBestAvailableQueueId();
    if (result == null) {
      throw new IllegalArgumentException("Queue is not available");
    }
    return Pair.of(result.getQueueId(), result.getCount());
  }
}
