package com.prashanth.ecommerce.security;

import com.prashanth.ecommerce.customer.Customer;
import com.prashanth.ecommerce.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Customer with email " + email + " not found"));
        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles(customer.getRole().name())
                .build();

    }

}
