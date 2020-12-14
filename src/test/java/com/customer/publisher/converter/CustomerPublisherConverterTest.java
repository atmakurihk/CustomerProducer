package com.customer.publisher.converter;


import com.customer.publisher.converters.CustomerKafkaRequestConverter;
import com.customer.publisher.model.Address;
import com.customer.publisher.model.Customer;
import com.customer.publisher.model.kafkaModel.CustomerRequestKafka;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CustomerPublisherConverterTest {

    private CustomerKafkaRequestConverter customerPublisherConverter;

    @BeforeEach
    public void setup(){
        customerPublisherConverter = new CustomerKafkaRequestConverter();
    }


    @Test
    public  void testConvertCustomerRequest()
    {
        CustomerRequestKafka customerRequestKafka = customerPublisherConverter.convert(createValidCustomerObject());
        Assertions.assertNotNull(customerRequestKafka);
    }

    private Customer createValidCustomerObject() {
        Customer customerRequest = new Customer();
        customerRequest.setCustomerNumber("C000000001");
        customerRequest.setBirthdate("10-12-2020");
        customerRequest.setCountry("India");
        customerRequest.setCountryCode("IN");
        customerRequest.setCustomerStatus(Customer.CustomerStatusEnum.OPEN);
        customerRequest.setEmail("test@gmail.com");
        customerRequest.setFirstName("FirstnameValid");
        customerRequest.setLastName("LastnameValid");
        customerRequest.setMobileNumber("9696969696");
        customerRequest.setAddress(createValidAddressObject());
        return customerRequest;
    }
    private Address createValidAddressObject() {

        Address address = new Address();
        address.setAddressLine1("addressLine1 address");
        address.setAddressLine2("customer_address_l2");
        address.setStreet("customer_address_street");
        address.setPostalCode("12345");

        return address;
    }
}
