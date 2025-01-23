package com.practice.jwt.global.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.jwt")
@Component
@Getter
@Setter
public class JwtProperties {
    private String issuer;
    private String secretKey;
}
