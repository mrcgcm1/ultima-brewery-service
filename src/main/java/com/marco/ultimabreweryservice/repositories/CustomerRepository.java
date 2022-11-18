package com.marco.ultimabreweryservice.repositories;

import com.marco.ultimabreweryservice.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {
}
