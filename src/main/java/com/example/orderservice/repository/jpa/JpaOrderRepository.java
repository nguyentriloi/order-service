package com.example.orderservice.repository.jpa;

import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.repository.jpa.entity.JpaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface JpaOrderRepository extends JpaRepository<JpaOrder, Long> {

  @Query("""
        UPDATE JpaOrder o SET o.status = :orderStatus WHERE o.id = :orderId
      """)
  @Modifying
  @Transactional
  void updateOrderStatusByOrderId(Long orderId, OrderStatus orderStatus);

  @Query("""
        UPDATE JpaOrder o SET o.status = :orderStatus WHERE o.id = :orderId AND o.status = :currentStatus
      """)
  @Modifying
  @Transactional
  void updateOrderStatusByIdAndCurrentStatus(Long orderId, OrderStatus orderStatus, OrderStatus currentStatus);
}
