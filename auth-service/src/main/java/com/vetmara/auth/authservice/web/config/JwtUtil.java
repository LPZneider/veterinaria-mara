package com.vetmara.auth.authservice.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final static String JWT_SECRET = "vetmara";

    private final static Algorithm ALGORITHM = Algorithm.HMAC256(JWT_SECRET);

    public String createJwt(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer("vetmara")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(15)))
                .sign(ALGORITHM);
    }

    public boolean verifyJwt(String token) {
        try {
            JWT.require(ALGORITHM).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return JWT.require(ALGORITHM).build().verify(token).getSubject();
    }
}
