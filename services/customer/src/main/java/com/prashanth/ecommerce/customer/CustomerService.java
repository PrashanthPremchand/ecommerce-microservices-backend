package com.prashanth.ecommerce.customer;


import com.prashanth.ecommerce.exception.CustomerNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;


    @CircuitBreaker(name = "customerService", fallbackMethod = "updateCustomerFallback")
    public void updateCustomer(@Valid CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Customer with id %s not found", request.id())
                ));
        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, @Valid CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if(StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if(request.address() != null){
            customer.setAddress(request.address());
        }
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "findAllCustomersFallback")
    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "existsByIdFallback")
    public Boolean existsById(String customerId) {
        return customerRepository.existsById(customerId);
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "existsByEmailFallback")
    public boolean existsByEmail(String email) {

        return customerRepository.existsByEmail(email);

    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "findByIdFallback")
    public CustomerResponse findById(String customerId) {
        return customerRepository.findById(customerId).map(mapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(format("Customer with id %s not found", customerId)));
    }

    @CircuitBreaker(name = "customerService", fallbackMethod ="findByEmailFallback" )
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new CustomerNotFoundException(format("Customer with email %s not found", email))
        );
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "deleteByIdFallback")
    public void deleteById(String customerId) {
        customerRepository.deleteById(customerId);
    }


    private void updateCustomerFallback(CustomerRequest request, Exception e) {
        log.error("Circuit breaker fallback - Unable to update customer: {}", e.getMessage());
        throw new RuntimeException("Customer service is currently unavailable. Please try again later.");
    }

    private List<CustomerResponse> findAllCustomersFallback(Exception e) {
        log.error("Circuit breaker fallback - Unable to fetch customers: {}", e.getMessage());
        return Collections.emptyList();
    }

    private Boolean existsByIdFallback(String customerId, Exception e) {
        log.error("Circuit breaker fallback - Unable to check customer existence by Id: {}", e.getMessage());
        return false;
    }

    private Boolean existsByEmailFallback(String email, Exception e) {
        log.error("Circuit breaker fallback - Unable to check customer existence by email: {}", e.getMessage());
        return false;
    }

    private CustomerResponse findByIdFallback(String customerId, Exception e) {
        log.error("Circuit breaker fallback - Unable to find customer by Id: {}", e.getMessage());
        throw new RuntimeException("Customer service is currently unavailable. Please try again later.");
    }

    private CustomerResponse findByEmailFallback(String customerId, Exception e) {
        log.error("Circuit breaker fallback - Unable to find customer by email: {}", e.getMessage());
        throw new RuntimeException("Customer service is currently unavailable. Please try again later.");
    }

    private void deleteByIdFallback(String customerId, Exception e) {
        log.error("Circuit breaker fallback - Unable to delete customer: {}", e.getMessage());
        throw new RuntimeException("Customer service is currently unavailable. Please try again later.");
    }

}
