package com.example.orderservice.event.consumer;

import com.example.orderservice.configuration.EventQueuesProperties;
import com.example.orderservice.message.request.UpdatePaymentEvent;
import com.example.orderservice.service.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {

  private final EventQueuesProperties eventQueuesProperties;

  private final OrderService orderService;

  @SqsListener("${events.queues.user-updated-payment-queue}")
  public void consumeUpdatePaymentEvent(UpdatePaymentEvent event) {
    log.info("Received message {} from queue {}",
        event, eventQueuesProperties.getUserUpdatedPaymentQueue());
    orderService.updateOrderPayment(event);
  }
}
