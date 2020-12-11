package com.customer.publisher.utils;

import com.customer.publisher.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataMaskUtil implements Converter<Customer,Customer> {

    private static  final String replaceString = "*";

    @Override
    public Customer convert(Customer customer) {
        Customer maskedCustomer = new Customer();
        BeanUtils.copyProperties(customer, maskedCustomer);

        maskedCustomer.setCustomerNumber(
                maskString(maskedCustomer.getCustomerNumber(),ApplicationConstants.CUSTOMER_MASK)
        );
        maskedCustomer.setBirthdate(
                maskString(maskedCustomer.getBirthdate(), ApplicationConstants.DOB_MASK)
        );
        maskedCustomer.setEmail(maskString(maskedCustomer.getEmail(), ApplicationConstants.EMAIL_MASK));

        return maskedCustomer;
    }

    private static String maskString(String maskString, String regex) {
        return maskString.replaceAll(regex, replaceString);
    }
}
