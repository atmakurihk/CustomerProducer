package com.customer.publisher.service;

import com.customer.publisher.model.Customer;
import com.customer.publisher.model.SuccessResponse;
import com.customer.publisher.model.kafkaModel.CustomerRequestKafka;

public interface CustomerKafkaPublisher {

    public SuccessResponse send(CustomerRequestKafka customer);
}
