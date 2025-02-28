package com.example.orderservice.repository.jpa.entity;

import com.example.orderservice.model.QueueStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "order_queue")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JpaOrderQueue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer queueId;

  private Long orderId;

  @Enumerated(EnumType.STRING)
  private QueueStatus status;

  private LocalDateTime estimatedReadyTime;

  private LocalDateTime createdDate;

  private LocalDateTime updatedDate;

  private String createdBy;

  private String updatedBy;
}
