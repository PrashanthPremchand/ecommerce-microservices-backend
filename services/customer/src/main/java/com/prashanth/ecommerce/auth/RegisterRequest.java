package com.prashanth.ecommerce.auth;

import com.prashanth.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {

    @NotBlank(message = "Firstname is required")
    String firstName;
    @NotBlank(message = "Lastname is required")
    String lastName;
    @Email(message = "This email format is incorrect")
    @NotBlank(message = "Email is required")
    String email;
    @NotBlank
    @Size(min = 8)
    String password;
    @NotBlank(message = "Address is required")
    Address address;

}
