package com.prashanth.ecommerce.payment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.validation.annotation.Validated;

@Validated
public record Customer(
        String id,
        @NotBlank(message = "Firstname is required")
        String firstname,
        @NotBlank(message = "Firstname is required")
        String lastname,
        @NotBlank(message = "Email is required")
        @Email(message = "Customer email is not correctly formated")
        String email
) {
}
