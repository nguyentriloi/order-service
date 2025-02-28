package com.example.orderservice.service;

import org.springframework.data.util.Pair;

public interface QueueService {

  Pair<Integer, Long> findBestQueueId();
}
