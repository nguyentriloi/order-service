package com.example.orderservice.service.mapper;

import com.example.orderservice.message.request.CreateOrderItemRequest;
import com.example.orderservice.model.OrderItem;
import com.example.orderservice.repository.jpa.entity.JpaOrderItem;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class OrderItemMapper {

  public OrderItem createOrderItem(CreateOrderItemRequest request) {
    if (request == null) {
      return null;
    }

    return OrderItem.builder()
        .menuItemId(request.getMenuItemId())
        .quantity(request.getQuantity())
        .specialInstructions(request.getSpecialInstructions())
        .build();
  }

  public List<OrderItem> createOrderItems(List<CreateOrderItemRequest> requests) {
    if (CollectionUtils.isEmpty(requests)) {
      return Collections.emptyList();
    }

    return requests.stream()
        .map(this::createOrderItem)
        .toList();
  }

  public List<JpaOrderItem> mapToJpaOrderItems(Long orderId, List<OrderItem> orderItems) {
    if (org.apache.commons.collections4.CollectionUtils.isEmpty(orderItems)) {
      return Collections.emptyList();
    }

    return orderItems
        .stream()
        .map(item -> JpaOrderItem.builder()
            .orderId(orderId)
            .menuItemId(item.getMenuItemId())
            .quantity(item.getQuantity())
            .specialInstructions(item.getSpecialInstructions())
            .build()
        )
        .toList();
  }
}
