package com.example.orderservice.adapter.customer;

import com.example.orderservice.message.response.CustomerDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customerClient", url = "http://localhost:8080")
public interface CustomerServiceClient {

  @GetMapping("/api/v1/customer/{id}")
  CustomerDetail getCustomerById(@PathVariable("id") Long id);
}
