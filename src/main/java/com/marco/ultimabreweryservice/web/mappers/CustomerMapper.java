package com.marco.ultimabreweryservice.web.mappers;

import com.marco.ultimabreweryservice.domain.Customer;
import com.marco.ultimabreweryservice.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer customer);
}
