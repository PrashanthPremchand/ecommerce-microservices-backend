package com.prashanth.ecommerce.customer;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    String houseNumber;
    String street;
    String zipCode;
}
