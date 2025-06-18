package com.homestaywithme.app.application.auth;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtUtil {
    private final JwtParser jwtParser;
    private final SecretKeySpec secretKeySpec;

    @Autowired
    public JwtUtil(@Value("${auth.secret-key}") String secretKey) {
        secretKeySpec = new SecretKeySpec(
                Base64.getDecoder().decode(Base64.getEncoder().encodeToString(secretKey.getBytes())),
                SignatureAlgorithm.HS256.getJcaName()
        );
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKeySpec)
                .build();
    }

    public String generateToken(AppUserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUserId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKeySpec, SignatureAlgorithm.HS256)
                .compact();
    }

    public Optional<Jws<Claims>> parse(String token) {
        try {
            var claims = jwtParser.parseClaimsJws(token);
            if(claims.getBody().getExpiration().before(new Date())) {
                return Optional.empty();
            }
            return Optional.of(claims);
        } catch (Exception e) {
            log.error("Cannot parse JWT", e);
            return Optional.empty();
        }
    }
}
