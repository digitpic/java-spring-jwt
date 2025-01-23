package com.practice.jwt.auth.util;

import com.practice.jwt.auth.domain.Member;
import com.practice.jwt.global.jwt.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperties properties;

    public String generate(final Member member, final Date expiry) {
        final Date now = new Date();

        return Jwts.builder()
                .header()
                    .add("typ", "JWT")
                    .add("alg", "HS512")
                .and()
                .issuer(properties.getIssuer())
                .issuedAt(now)
                .notBefore(now)
                .expiration(expiry)
                .subject(member.getEmail())
                .claim("id", member.getId())
                .claim("nickname", member.getUsername())
                .signWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()), Jwts.SIG.HS512)
                .compact();
    }

    public boolean validate(final String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(properties.getSecretKey().getBytes()))
                    .build()
                    .parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
