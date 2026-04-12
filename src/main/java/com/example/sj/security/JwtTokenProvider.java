package com.example.sj.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import javax.crypto.SecretKey;

/**
 * JWT Token Provider - Simple token generation, extraction, and validation
 */
@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret:mySecretKeyForJWTTokenGenerationAndValidationPurposeOnly}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:86400000}")
    private int jwtExpirationInMs;
    
    // Get Secret Key (Reusable)
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    // Get JWT Parser (Reusable)
    private JwtParser getParser() {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
    }
    
    /**
     * Generate Token from Authentication (Email from auth object)
     */
    public String generateToken(Authentication authentication) {
        return generateTokenFromEmail(authentication.getName());
    }
    
    /**
     * Generate Token from Email (Direct)
     */
    public String generateTokenFromEmail(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    
    /**
     * Extract Email from Token
     */
    public String getEmailFromToken(String token) {
        return getParser()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    
    /**
     * Validate Token (Check signature, expiration, format)
     * Algorithm: HS512
     */
    public boolean validateToken(String token) {
        try {
            getParser().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
