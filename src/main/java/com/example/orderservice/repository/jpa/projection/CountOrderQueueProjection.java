package com.example.orderservice.repository.jpa.projection;

public interface CountOrderQueueProjection {

  Integer getQueueId();

  Long getCount();
}
