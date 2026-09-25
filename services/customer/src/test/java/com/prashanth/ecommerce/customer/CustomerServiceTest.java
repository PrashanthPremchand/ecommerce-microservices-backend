package com.prashanth.ecommerce.customer;

import com.prashanth.ecommerce.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerRequest request;
    private CustomerResponse response;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id("cust-1")
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();

        request = new CustomerRequest("cust-1", "John", "Doe", "john@example.com", null);
        response = new CustomerResponse("cust-1", "John", "Doe", "john@example.com", null);
    }

    @Test
    void findAllCustomers_shouldReturnMappedList() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(mapper.toCustomerResponse(customer)).thenReturn(response);

        List<CustomerResponse> result = customerService.findAllCustomers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).email()).isEqualTo("john@example.com");
    }

    @Test
    void findById_shouldReturnCustomer_whenExists() {
        when(customerRepository.findById("cust-1")).thenReturn(Optional.of(customer));
        when(mapper.toCustomerResponse(customer)).thenReturn(response);

        CustomerResponse result = customerService.findById("cust-1");

        assertThat(result.id()).isEqualTo("cust-1");
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        when(customerRepository.findById("bad-id")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findById("bad-id"))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("bad-id");
    }

    @Test
    void existsById_shouldReturnTrue_whenCustomerExists() {
        when(customerRepository.existsById("cust-1")).thenReturn(true);

        assertThat(customerService.existsById("cust-1")).isTrue();
    }

    @Test
    void existsByEmail_shouldReturnFalse_whenEmailNotFound() {
        when(customerRepository.existsByEmail("missing@example.com")).thenReturn(false);

        assertThat(customerService.existsByEmail("missing@example.com")).isFalse();
    }

    @Test
    void updateCustomer_shouldThrow_whenCustomerNotFound() {
        when(customerRepository.findById("cust-1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.updateCustomer(request))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("cust-1");
    }

    @Test
    void updateCustomer_shouldSave_whenCustomerExists() {
        when(customerRepository.findById("cust-1")).thenReturn(Optional.of(customer));

        customerService.updateCustomer(request);

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void deleteById_shouldCallRepository() {
        doNothing().when(customerRepository).deleteById("cust-1");

        customerService.deleteById("cust-1");

        verify(customerRepository, times(1)).deleteById("cust-1");
    }

    @Test
    void findByEmail_shouldReturnCustomer_whenExists() {
        when(customerRepository.findByEmail("john@example.com")).thenReturn(Optional.of(customer));

        Customer result = customerService.findByEmail("john@example.com");

        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void findByEmail_shouldThrow_whenNotFound() {
        when(customerRepository.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> customerService.findByEmail("ghost@example.com"))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessageContaining("ghost@example.com");
    }
}