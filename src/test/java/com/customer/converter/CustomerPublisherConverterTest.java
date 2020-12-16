package com.customer.converter;

import com.customer.converters.CustomerKafkaRequestConverter;
import com.customer.model.Address;
import com.customer.model.Customer;
import com.customer.model.kafkaModel.CustomerRequestKafka;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerPublisherConverterTest {

  private CustomerKafkaRequestConverter customerPublisherConverter;

  @BeforeEach
  public void setup() {
    customerPublisherConverter = new CustomerKafkaRequestConverter();
  }

  @Test
  public void testConvertCustomerRequest() {
    CustomerRequestKafka customerRequestKafka =
        customerPublisherConverter.convert(createValidCustomerObject());
    assertNotNull(customerRequestKafka);
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
