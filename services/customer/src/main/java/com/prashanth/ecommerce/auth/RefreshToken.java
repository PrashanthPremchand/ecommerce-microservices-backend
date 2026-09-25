package com.prashanth.ecommerce.auth;

import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(collation = "refresh_tokens")
public class RefreshToken {

    @Id
    String id;
    String customerId;
    String tokenHash;
    Instant expiresAt;
    Instant createdAt;
    boolean revoked;

}
