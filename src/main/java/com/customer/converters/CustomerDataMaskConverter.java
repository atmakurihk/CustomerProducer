package com.customer.converters;

import com.customer.model.Customer;
import com.customer.utils.ApplicationConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerDataMaskConverter implements Converter<Customer, Customer> {

  private static final String replaceString = "*";

  private static String maskString(String maskString, String regex) {
    return maskString.replaceAll(regex, replaceString);
  }

  @Override
  public Customer convert(Customer customer) {
    Customer maskedCustomer = new Customer();
    BeanUtils.copyProperties(customer, maskedCustomer);

    maskedCustomer.setCustomerNumber(
        maskString(maskedCustomer.getCustomerNumber(), ApplicationConstants.CUSTOMER_MASK));
    maskedCustomer.setBirthdate(
        maskString(maskedCustomer.getBirthdate(), ApplicationConstants.DOB_MASK));
    maskedCustomer.setEmail(maskString(maskedCustomer.getEmail(), ApplicationConstants.EMAIL_MASK));

    return maskedCustomer;
  }
}
