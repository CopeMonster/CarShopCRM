package me.alanton.carshopcrm.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import me.alanton.carshopcrm.entity.Role;
import me.alanton.carshopcrm.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${application.auth.access.secret}")
    private String accessSecret;

    @Value("${application.auth.refresh.secret}")
    private String refreshSecret;

    @Value("${application.auth.access.expiration-time}")
    private Long accessExpirationTime;

    @Value("${application.auth.refresh.expiration-time}")
    private Long refreshExpirationTime;

    public String extractUsername(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getSubject, isAccessToken);
    }

    public String generateAccessToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
            claims.put("roles", customUserDetails.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet()));
        }
        return generateToken(claims, userDetails, true);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User customUserDetails) {
            claims.put("id", customUserDetails.getId());
            claims.put("email", customUserDetails.getEmail());
        }
        return generateToken(claims, userDetails, false);
    }

    public boolean isTokenValid(String token, UserDetails userDetails, boolean isAccessToken) {
        final String username = extractUsername(token, isAccessToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, isAccessToken);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean isAccessToken) {
        final Claims claims = extractAllClaims(token, isAccessToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails, boolean isAccessToken) {
        Instant expirationToken = LocalDateTime.now()
                .plusSeconds(isAccessToken ? accessExpirationTime : refreshExpirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .expiration(Date.from(expirationToken))
                .signWith(getSigningKey(isAccessToken))
                .compact();
    }

    public boolean isTokenExpired(String token, boolean isAccessToken) {
        return extractExpiration(token, isAccessToken).before(new Date());
    }

    public Date extractExpiration(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getExpiration, isAccessToken);
    }

    public Claims extractAllClaims(String token, boolean isAccessToken) {
        return Jwts.parser()
                .verifyWith(getSigningKey(isAccessToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey(boolean isAccessToken) {
        byte[] keyBytes = Decoders.BASE64.decode(isAccessToken ? accessSecret : refreshSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}