package com.example.orderservice.service.impl;

import com.example.orderservice.event.producer.OrderProducer;
import com.example.orderservice.message.request.CreateOrderRequest;
import com.example.orderservice.message.request.UpdatePaymentEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderQueue;
import com.example.orderservice.model.OrderStatus;
import com.example.orderservice.model.PaymentMethod;
import com.example.orderservice.model.PaymentStatus;
import com.example.orderservice.model.QueueStatus;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.repository.QueueRepository;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.service.QueueService;
import com.example.orderservice.service.mapper.OrderMapper;
import com.example.orderservice.service.validator.OrderValidator;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderValidator orderValidator;

  private final OrderMapper orderMapper;

  private final OrderRepository orderRepository;

  private final QueueRepository queueRepository;

  private final QueueService queueService;

  private final OrderProducer orderProducer;

  @Override
  public Long createOrder(CreateOrderRequest request) {
    log.info("Starting create order for customer {}", request.getCustomerId());
    orderValidator.validateCreateOrder(request);

    val order = orderMapper.createOrder(request);
    val updatedOrder = updatePaymentProcess(order, request.getPaymentMethod());

    val savedOrderId = orderRepository.save(updatedOrder);

    orderProducer.pushCreatePaymentEvent(savedOrderId,
        request.getPaymentMethod(), order.getTotalAmount());

    log.info("End create order for customer {} with status {}",
        updatedOrder.getCustomerId(),
        updatedOrder.getStatus());
    return savedOrderId;
  }

  @Override
  @Transactional
  public void updateOrderPayment(UpdatePaymentEvent event) {

    if (PaymentStatus.FAILED == event.status()) {
      orderRepository.updateOrderStatusById(event.orderId(), OrderStatus.FAILED);
    }

    val availableQueue = getBestAvailableQueue();
    queueRepository.saveQueue(event.orderId(), availableQueue);
    orderRepository.updateOrderStatusById(event.orderId(), OrderStatus.IN_QUEUE);
  }

  @Override
  public void readyForPickup(Long orderId) {
    queueRepository.updateQueueItemStatus(orderId, QueueStatus.READY);
  }

  @Override
  public void pickup(Long id) {
    orderProducer.completedPaymentOrder(id);
    orderRepository.updateOrderStatusByIdAndCurrentStatus(id, OrderStatus.PICKED_UP,
        OrderStatus.IN_QUEUE);
  }

  @Override
  public void complete(Long id) {
    orderRepository.updateOrderStatusByIdAndCurrentStatus(id, OrderStatus.COMPLETED,
        OrderStatus.PICKED_UP);
  }

  private Order updatePaymentProcess(Order order, PaymentMethod paymentMethod) {
    if (PaymentMethod.COD == paymentMethod) {
      return order.toBuilder()
          .orderQueue(getBestAvailableQueue())
          .status(OrderStatus.IN_QUEUE)
          .build();
    }
    if (PaymentMethod.DIGITAL == paymentMethod) {
      return order.toBuilder()
          .status(OrderStatus.AWAITING_PAYMENT)
          .build();
    }
    log.error("Payment method {} is not supported", paymentMethod.name());
    throw new IllegalArgumentException("Payment method is not supported");
  }

  private OrderQueue getBestAvailableQueue() {

    val pairQueueIdAndCount = queueService.findBestQueueId();
    val estimatedReadyTime = calculateReadyTime(pairQueueIdAndCount.getSecond());
    log.info("Estimated new order ready time is {}", estimatedReadyTime);
    return OrderQueue.builder()
        .queueId(pairQueueIdAndCount.getFirst())
        .status(QueueStatus.PREPARING)
        .estimatedReadyTime(estimatedReadyTime)
        .build();
  }

  private LocalDateTime calculateReadyTime(Long numOfPreparingOrder) {
    val minutes = numOfPreparingOrder * 10 + 10;
    return LocalDateTime.now().plusMinutes(minutes);
  }
}
