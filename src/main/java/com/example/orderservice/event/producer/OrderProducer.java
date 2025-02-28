package com.example.orderservice.event.producer;

import com.example.orderservice.configuration.EventQueuesProperties;
import com.example.orderservice.message.request.CreatePaymentEvent;
import com.example.orderservice.model.PaymentMethod;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderProducer {

  private final EventQueuesProperties eventQueuesProperties;

  private final SqsTemplate sqsTemplate;

  public void pushCreatePaymentEvent(Long orderId, PaymentMethod paymentMethod, BigDecimal amount) {
    val event = new CreatePaymentEvent(orderId, paymentMethod, amount);
    val queueName = eventQueuesProperties.getUserCreatedPaymentQueue();
    sqsTemplate.send(to -> to.queue(queueName).payload(event));
    log.info("Create Payment message {} is sent to queue {}", event, queueName);
  }

  public void completedPaymentOrder(Long id) {
    val queueName = eventQueuesProperties.getUserCompletedPaymentQueue();
    sqsTemplate.send(to -> to.queue(queueName).payload(id));
    log.info("Complete Payment Message {} is sent to queue {}", id, queueName);
  }
}
