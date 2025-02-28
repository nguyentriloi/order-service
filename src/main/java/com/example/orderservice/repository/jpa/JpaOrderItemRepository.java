package com.example.orderservice.repository.jpa;

import com.example.orderservice.repository.jpa.entity.JpaOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderItemRepository extends JpaRepository<JpaOrderItem, Long> {

}
