package com.customer.publisher.controller;

import com.customer.publisher.converters.CustomerDataMaskConverter;
import com.customer.publisher.converters.CustomerKafkaRequestConverter;
import com.customer.publisher.exception.CustomerExceptionHandler;
import com.customer.publisher.model.Address;
import com.customer.publisher.model.Customer;
import com.customer.publisher.model.kafkaModel.CustomerRequestKafka;
import com.customer.publisher.service.CustomerKafkaPublisher;
import com.customer.publisher.utils.ObjectMapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    CustomerController customerController;

    @Mock
   private CustomerDataMaskConverter customerDataMaskConverter;

    @Mock
    private  CustomerKafkaRequestConverter customerKafkaRequestConverter;

    @Mock
    private CustomerKafkaPublisher customerKafkaPublisher;

    @BeforeEach
    public void  setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(new CustomerExceptionHandler())
                .build();

    }

    @Test
    public void shouldReturnSuccessWhenValidRequest() throws Exception {
        Mockito.doReturn(new CustomerRequestKafka())
                .when(customerKafkaRequestConverter)
                .convert(Mockito.any(Customer.class));

        MockHttpServletResponse response = mockMvc.perform(post("/customer/create")
                .content(ObjectMapperUtil.returnJsonFromObject(createInvalidRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createHeaders())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public  void shouldReturnBadrequest() throws  Exception
    {
        MockHttpServletResponse response = mockMvc.perform(post("/customer/create")
                .content(ObjectMapperUtil.returnJsonFromObject(createValidCustomerObject()))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createHeaders())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void shouldreturn404NotFound() throws Exception
    {
        MockHttpServletResponse response = mockMvc.perform(post("/customer/crea")
                .content(ObjectMapperUtil.returnJsonFromObject(createValidCustomerObject()))
                .contentType(MediaType.APPLICATION_JSON)
                .headers(createInvalidHeaders())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
    private Customer createValidCustomerObject() {
        Customer customerRequest = new Customer();
        customerRequest.setCustomerNumber("C000000001");
        customerRequest.setBirthdate("10-12-202");
        customerRequest.setCountry("India");
        customerRequest.setCountryCode("IN");
        customerRequest.setCustomerStatus(Customer.CustomerStatusEnum.OPEN);
        customerRequest.setEmail("customer@gmail.com");
        customerRequest.setFirstName("FirstnameValid");
        customerRequest.setLastName("LastnameValid");
        customerRequest.setMobileNumber("9696969696");
        customerRequest.setAddress(createValidAddressObject());
        return customerRequest;
    }
    private Customer createInvalidRequest() {
        Customer customerRequest = new Customer();
        customerRequest.setCustomerNumber("C0000001");
        customerRequest.setBirthdate("10-12-2020");
        customerRequest.setCountry("India");
        customerRequest.setCountryCode("IN");
        customerRequest.setCustomerStatus(Customer.CustomerStatusEnum.OPEN);
        customerRequest.setEmail("customer@gmail.com");
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

    private HttpHeaders createHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "bearer 2b7af75e-e97e-4033-956f-7e9b9f0f00ed");
        httpHeaders.add("Activity-id", "activity");
        httpHeaders.add("Transaction-id", "transaction");
        return httpHeaders;
    }

    private HttpHeaders createInvalidHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "bearer");
        httpHeaders.add("Activity-id", "activity");
        httpHeaders.add("Transaction-id", "transaction");
        return httpHeaders;
    }
}
