package com.customer.publisher.service;

import com.customer.publisher.model.SuccessResponse;
import com.customer.publisher.model.kafkaModel.CustomerRequestKafka;
import com.customer.publisher.service.impl.CustomerKafkaPublisherImpl;
import com.customer.publisher.utils.ApplicationConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class CustomerKafkaPublisherImplTest {

    @InjectMocks
    private CustomerKafkaPublisherImpl customerKafkaPublisher;

    @Mock
    private KafkaTemplate<String, CustomerRequestKafka> kafkaTemplate;


    @Test
    public void testSuccessCustomerResponseWhenPublishDataToKafka(){
        SuccessResponse successResponse = customerKafkaPublisher.send(Mockito.any(CustomerRequestKafka.class));
        assertThat(successResponse.getStatus()).isEqualTo("Success");
    }
}
