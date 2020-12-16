package com.customer.controller;

import com.customer.converters.CustomerDataMaskConverter;
import com.customer.converters.CustomerKafkaRequestConverter;
import com.customer.model.Customer;
import com.customer.model.SuccessResponse;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import com.customer.service.CustomerKafkaPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

  @Autowired private CustomerKafkaPublisher customerKafkaPublisher;

  @Autowired private CustomerDataMaskConverter customerDataMaskConverter;

  @Autowired private CustomerKafkaRequestConverter customerKafkaRequestConverter;

  @PostMapping(
      path = "/create",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> createCustomer(
      @RequestHeader(value = "Authorization") String authorization,
      @RequestHeader(value = "Activity-id") String activityId,
      @RequestHeader(value = "Transaction-id") String transactionId,
      @Valid @RequestBody Customer customer) {
    LOGGER.info("incoming request {}", customerDataMaskConverter.convert(customer));
    CustomerRequestKafka customerRequestKafka = customerKafkaRequestConverter.convert(customer);
    customerRequestKafka.setActivityId(activityId);
    customerRequestKafka.setTransactionId(transactionId);

    SuccessResponse successResponse = customerKafkaPublisher.send(customerRequestKafka);
    LOGGER.info("out going response {}", successResponse);
    return new ResponseEntity<>(successResponse, HttpStatus.OK);
  }
}
