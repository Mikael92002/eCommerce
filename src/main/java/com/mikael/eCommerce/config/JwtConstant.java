package com.mikael.eCommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstant {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.header}")
    private String header;

    public String getHeader() {
        return header;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
