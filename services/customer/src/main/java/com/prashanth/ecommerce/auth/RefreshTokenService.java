package com.prashanth.ecommerce.auth;

import com.prashanth.ecommerce.exception.InvalidRefreshTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final SecureRandom secureRandom = new SecureRandom();
    private final MongoTemplate mongoTemplate;

    @Value("${application.security.refresh-token.expiration}")
    private long refreshTokenExpiration;



    public RefreshToken consumeRefreshToken(String rawToken) {

        String tokenHash = hashToken(rawToken);
        Instant now = Instant.now();
        Query query = new Query(
                Criteria.where("tokenHash").is(tokenHash)
                        .and("revoked").is(false)
                        .and("expiresAt").gt(now)
        );
        Update update = new Update()
                .set("revoked", true);
        FindAndModifyOptions options = FindAndModifyOptions
                        .options()
                        .returnNew(false);
        RefreshToken consumedToken = mongoTemplate.findAndModify(
                query,
                update,
                options,
                RefreshToken.class
        );
        if (consumedToken == null) {
            throw new InvalidRefreshTokenException("Invalid or already consumed refresh token");
        }
        return consumedToken;

    }

    public RefreshToken findToken(String rawToken) {

        String tokenHash = hashToken(rawToken);
        return refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow( () -> new InvalidRefreshTokenException("Invalid refresh token"));


    }

    public RefreshToken validateRefreshToken(String rawToken) {

        RefreshToken refreshToken = findToken(rawToken);
        if (refreshToken.isRevoked()) {
            throw new InvalidRefreshTokenException("Token is revoked");
        }
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Token is expired");
        }

        return refreshToken;

    }

    public String createRefreshToken(String customerId) {

        String rawToken = generateToken();
        String tokenHash = hashToken(rawToken);
        Instant now = Instant.now();
        RefreshToken refreshToken = RefreshToken.builder()
                .customerId(customerId)
                .tokenHash(tokenHash)
                .createdAt(now)
                .expiresAt(now.plusMillis(refreshTokenExpiration))
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);
        return rawToken;

    }

    private String hashToken(String rawToken) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }

    }

    private String generateToken() {

        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);

    }


}
