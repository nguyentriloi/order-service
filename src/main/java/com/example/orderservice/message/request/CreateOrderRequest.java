package com.example.orderservice.message.request;

import com.example.orderservice.model.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CreateOrderRequest {

  @NotNull
  private Long customerId;

  @NotNull
  private BigDecimal totalAmount;

  @NotEmpty
  private List<@Valid CreateOrderItemRequest> items;

  @NotNull
  private PaymentMethod paymentMethod;
}
