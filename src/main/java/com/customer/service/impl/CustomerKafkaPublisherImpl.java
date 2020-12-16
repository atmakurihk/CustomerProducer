package com.customer.service.impl;

import com.customer.exception.GenericException;
import com.customer.model.SuccessResponse;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.CustomerKafkaPublisher;
import com.customer.utils.ApplicationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerKafkaPublisherImpl implements CustomerKafkaPublisher {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerKafkaPublisherImpl.class);

  @Autowired private KafkaTemplate<String, CustomerRequestKafka> kafkaTemplate;

  @Value("${cloudkarafka.topic}")
  private String topic;

  public SuccessResponse send(CustomerRequestKafka message) {

    try {
      kafkaTemplate.send(topic, message);
      LOGGER.info("Sent message to topic: " + topic);
      return customerResponse();
    } catch (KafkaException e) {
      LOGGER.error("Error while publishing customer data to kafka. {}", e);
      throw new GenericException(
          "Error while publishing customer data to kafka. " + e.getMessage());
    }
  }

  private SuccessResponse customerResponse() {
    SuccessResponse successResponse = new SuccessResponse();
    successResponse.setStatus(ApplicationConstants.SUCCESS);
    successResponse.setMessage("Customer data has been published successfully");
    return successResponse;
  }
}
