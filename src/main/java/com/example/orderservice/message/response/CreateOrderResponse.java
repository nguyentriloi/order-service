package com.example.orderservice.message.response;

import com.example.orderservice.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponse {

    private Long customerId;

    private OrderStatus status;
}
