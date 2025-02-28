package com.example.orderservice.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  private Long id;

  private Long customerId;

  private BigDecimal totalAmount;

  private OrderStatus status;

  private LocalDateTime pickupTime;

  private LocalDateTime orderDate;

  private List<OrderItem> orderItems;

  private OrderQueue orderQueue;
}
