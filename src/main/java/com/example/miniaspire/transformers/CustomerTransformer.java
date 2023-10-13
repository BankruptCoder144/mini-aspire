package com.example.miniaspire.transformers;

import com.example.miniaspire.dto.CustomerDto;
import com.example.miniaspire.entities.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerTransformer {

    public CustomerDto transformCustomerToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        return customerDto;
    }

    public List<CustomerDto> transformListOfCustomerToCustomerDto(List<Customer> customers) {
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(customer -> {
            customerDtos.add(transformCustomerToCustomerDto(customer));
        });
        return customerDtos;
    }


}
