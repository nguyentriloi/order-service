package com.example.orderservice.controller;

import com.example.orderservice.message.request.CreateOrderRequest;
import com.example.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;


  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<Long> createOrder(@RequestBody @Valid CreateOrderRequest request) {
    val orderId = orderService.createOrder(request);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{id}/ready")
  @PreAuthorize("hasAuthority('ROLE_STAFF')")
  public ResponseEntity<Void> readyForPickup(@PathVariable Long id) {
    orderService.readyForPickup(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{id}/pickup")
  @PreAuthorize("hasAuthority('ROLE_STAFF')")
  public ResponseEntity<Void> pickup(@PathVariable Long id) {
    orderService.pickup(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PutMapping("/{id}/complete")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<Void> complete(@PathVariable Long id) {
    orderService.complete(id);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
