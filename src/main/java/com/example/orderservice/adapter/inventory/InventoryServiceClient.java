package com.example.orderservice.adapter.inventory;

import com.example.orderservice.message.request.CreateOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "inventoryClient", url = "http://localhost:8080")
public interface InventoryServiceClient {

  @PutMapping("/api/v1/inventory/reserve")
  Boolean reserveItems(@RequestBody CreateOrderRequest request);
}
