package com.customer.publisher.converter;

import com.customer.publisher.converters.CustomerDataMaskConverter;
import com.customer.publisher.model.Address;
import com.customer.publisher.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerDataMaskConverterTest {

    private CustomerDataMaskConverter customerDataMaskConverter;

    @BeforeEach
    public void setup(){
        customerDataMaskConverter = new CustomerDataMaskConverter();
    }


    @Test
    public void maskingLogicTestWithValidCustomer(){
        Customer maskedCustomer = customerDataMaskConverter.convert(createValidCustomerObject());
        assertNotNull(maskedCustomer);
        assertEquals("C00000**", maskedCustomer.getCustomerNumber());
        assertEquals("*-2020", maskedCustomer.getBirthdate());
        assertEquals("*@gmail.com", maskedCustomer.getEmail());
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
