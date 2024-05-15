package com.scalpnote.domain.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomTokenProviderService {

    @Value("${app.auth.token-secret}")
    private String jwtSecret;

    @Value("${app.auth.access-token-expiration-msec}")
    private int jwtExpirationInMs;

    private final CustomUserDetailsService customUserDetailsService;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        log.info("JWT Secret: {}", jwtSecret);
    }


    public boolean validateToken(String authToken) {
        if (!StringUtils.hasText(authToken)) {
            log.warn("Token is null or empty");
            return false; // 토큰이 null 또는 빈 문자열이면 유효하지 않음
        }
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            log.error("Token validation error: ", ex);
            return false;
        }
    }


    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        List<String> roles = claims.get("roles", List.class);
        if (roles == null) {
            roles = Collections.emptyList(); // or handle this case as needed
        }
        log.info("Extracted roles from token: {}", roles);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }


    public UsernamePasswordAuthenticationToken getAuthenticationById(String token) {
        Long userId = getUserIdFromToken(token);
        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return authentication;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }
}
