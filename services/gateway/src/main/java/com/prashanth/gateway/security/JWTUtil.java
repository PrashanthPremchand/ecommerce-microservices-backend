package com.prashanth.gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private Key getSignInKey() {

        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    public  String extractUsername(String token) {

        return extractAllClaims(token).getSubject();

    }

    public String extractRole(String token) {

        return extractAllClaims(token).get("role").toString();

    }

    public boolean isTokenValid(String token) {
        System.out.println("Secret key being used: " + secretKey); // ADD THIS
        try {
            Claims claims = extractAllClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            System.out.println("Token validation error: " + e.getMessage()); // ADD THIS
            return false;
        }
    }

}
