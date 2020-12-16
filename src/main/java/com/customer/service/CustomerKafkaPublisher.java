package com.customer.service;

import com.customer.model.SuccessResponse;
import com.customer.model.kafkaModel.CustomerRequestKafka;

public interface CustomerKafkaPublisher {

  public SuccessResponse send(CustomerRequestKafka customer);
}
