package com.example.orderservice.repository.jpa;

import com.example.orderservice.model.QueueStatus;
import com.example.orderservice.repository.jpa.entity.JpaOrderQueue;
import com.example.orderservice.repository.jpa.projection.CountOrderQueueProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaOrderQueueRepository extends JpaRepository<JpaOrderQueue, Long> {

  @Query("""
        SELECT q.id AS queueId, COUNT(o.id) AS count
        FROM JpaQueue q
        LEFT JOIN JpaOrderQueue o ON q.id = o.queueId AND o.status = 'PREPARING'
        WHERE q.status = 'ACTIVE'
        GROUP BY q.id
        ORDER BY count ASC
      """)
  Page<CountOrderQueueProjection> findBestAvailableQueueId(Pageable pageable);

  @Query("""
        UPDATE JpaOrderQueue o SET o.status = :status WHERE o.orderId = :orderId
      """)
  @Modifying
  @Transactional
  void updateQueueItemStatus(Long orderId, QueueStatus status);
}
