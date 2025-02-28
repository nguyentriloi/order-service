package com.example.orderservice.service.validator;

import com.example.orderservice.adapter.customer.CustomerServiceClient;
import com.example.orderservice.adapter.inventory.InventoryServiceClient;
import com.example.orderservice.message.request.CreateOrderRequest;
import com.example.orderservice.model.Status;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderValidator {

  private final CustomerServiceClient customerClient;

  private final InventoryServiceClient inventoryClient;

  public void validateCreateOrder(@NonNull CreateOrderRequest request) {

    if (CollectionUtils.isEmpty(request.getItems())) {
      return;
    }

    /* Mock

    validateCustomer(request.getCustomerId());
    reserveItems(request);

    */

  }

  private void validateCustomer(long customerId) {
    val customer = customerClient.getCustomerById(customerId);
    if (customer != null && Status.ACTIVE == customer.getStatus()) {
      return;
    }

    throw new IllegalArgumentException("Customer is invalid");
  }

  private void reserveItems(CreateOrderRequest request) {
    val result = inventoryClient.reserveItems(request);
    if (!result) {
      throw new IllegalArgumentException("Item is out of stock");
    }
  }
}
