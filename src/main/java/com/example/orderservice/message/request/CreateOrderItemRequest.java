package com.example.orderservice.message.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderItemRequest {

  @NotNull
  private Long menuItemId;

  @Min(1)
  private int quantity;

  private String specialInstructions;
}
