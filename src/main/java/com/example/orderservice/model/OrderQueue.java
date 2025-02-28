package com.example.orderservice.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueue {

  private Long id;

  private Integer queueId;

  private Long orderId;

  private QueueStatus status;

  private LocalDateTime estimatedReadyTime;
}
