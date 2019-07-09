package com.greenfox.javatribes.javatribes.security;

import com.greenfox.javatribes.javatribes.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTTokenUtil {

    private String token;

    public JWTTokenUtil() {
    }

    private void createJWT(User user) {

        byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

        this.token = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .setIssuer(SecurityConstants.TOKEN_ISSUER)
                .setAudience(SecurityConstants.TOKEN_AUDIENCE)
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                .claim("rol", "USER")
                .compact();
    }

    public String getToken(User user) {
        createJWT(user);
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}