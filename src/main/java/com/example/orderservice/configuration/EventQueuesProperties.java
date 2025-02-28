package com.example.orderservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "events.queues")
@Getter
@Setter
public class EventQueuesProperties {

  private String userCreatedPaymentQueue;

  private String userUpdatedPaymentQueue;

  private String userCompletedPaymentQueue;
}
