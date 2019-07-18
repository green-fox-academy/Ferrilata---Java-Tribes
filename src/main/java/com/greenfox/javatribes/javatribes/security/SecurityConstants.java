package com.greenfox.javatribes.javatribes.security;

public final class SecurityConstants {

    static final String JWT_SECRET = "KaPdSgVkXp2s5v8y/B?E(H+MbQeThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%D";

    // JWT token defaults
    static final String TOKEN_HEADER = "token";
    static final String TOKEN_PREFIX = "";
    static final String TOKEN_TYPE = "JWT";
    static final String TOKEN_ISSUER = "tribes-api";
    static final String TOKEN_AUDIENCE = "tribes-app";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
