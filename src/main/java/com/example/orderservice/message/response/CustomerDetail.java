package com.example.orderservice.message.response;

import com.example.orderservice.model.Status;
import lombok.Data;

@Data
public class CustomerDetail {

    private Long id;

    private String name;

    private Status status;
}
