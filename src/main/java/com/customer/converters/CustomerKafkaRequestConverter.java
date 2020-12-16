    package com.customer.converters;

    import com.customer.model.Customer;
    import com.customer.model.kafkaModel.CustomerRequestKafka;
    import org.springframework.beans.BeanUtils;
    import org.springframework.core.convert.converter.Converter;
    import org.springframework.stereotype.Component;

    @Component
    public class CustomerKafkaRequestConverter implements Converter<Customer, CustomerRequestKafka> {
        @Override
        public CustomerRequestKafka convert(Customer customer) {
            CustomerRequestKafka customerRequestKafka = new CustomerRequestKafka();
            BeanUtils.copyProperties(customer, customerRequestKafka);
            customerRequestKafka.setCustomerStatus(
                    CustomerRequestKafka.CustomerStatusEnum.fromValue(customer.getCustomerStatus().toString())
            );
            return customerRequestKafka;
        }
    }
