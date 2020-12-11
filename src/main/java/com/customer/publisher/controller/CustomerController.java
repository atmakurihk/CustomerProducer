package com.customer.publisher.controller;

import com.customer.publisher.model.Customer;
import com.customer.publisher.model.kafkaModel.CustomerRequestKafka;
import com.customer.publisher.service.Producer;
import com.customer.publisher.utils.CustomerDataMaskUtil;
import com.customer.publisher.utils.CustomerKafkaRequestConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customer")
public class CustomerController {

    private  static  final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private Producer producer;

    @Autowired
    private CustomerDataMaskUtil customerDataMaskUtil;

    @Autowired
    private CustomerKafkaRequestConverter customerKafkaRequestConverter;

    @PostMapping(path = "/create",produces =MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Customer createCustomer(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestHeader(value = "Activity-id",required = false) String activityId,
            @RequestHeader(value = "Transaction-id",required = false) String transactionId,
            @Valid @RequestBody  Customer customer)
    {
        LOGGER.info("incoming request {}",customerDataMaskUtil.convert(customer));
        CustomerRequestKafka  customerRequestKafka = customerKafkaRequestConverter.convert(customer);
        customerRequestKafka.setActivityId(activityId);
        customerRequestKafka.setTransactionId(transactionId);

        producer.send(customer);
        LOGGER.info("out going response {}",customerDataMaskUtil.convert(customer));
        return customer;
    }

}