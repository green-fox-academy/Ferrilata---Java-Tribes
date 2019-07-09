package com.greenfox.javatribes.javatribes.security;



public final class SecurityConstants {

    static final String JWT_SECRET = "KaPdSgVkXp2s5v8y/B?E(H+MbQeThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%D";

    // JWT token defaults
//    static final String TOKEN_HEADER = "Authorization";
//    static final String TOKEN_PREFIX = "Bearer ";
    static final String TOKEN_TYPE = "JWT";
    static final String TOKEN_ISSUER = "secure-api";
    static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}