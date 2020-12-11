package com.customer.publisher.service.impl;

import com.customer.publisher.exception.GenericException;
import com.customer.publisher.model.Customer;
import com.customer.publisher.service.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProducerImpl implements Producer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerImpl.class);

    @Value("${cloudkarafka.topic}")
    private String topic;

    private ObjectMapper mapper = new ObjectMapper();


    ProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Customer message) {
        String customerKakfaJson = null;
        try{
            customerKakfaJson = mapper.writeValueAsString(message);
        }catch (Exception e)
        {
            LOGGER.error("Exception occurred in producer send {}",e.getMessage());
       throw new GenericException("Exception occurred while sending message to producer");
        }

        this.kafkaTemplate.send(topic, customerKakfaJson);
        LOGGER.info("Sent message to topic: " + topic);
    }
}
