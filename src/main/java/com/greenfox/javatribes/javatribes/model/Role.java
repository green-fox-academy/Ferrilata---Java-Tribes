package com.greenfox.javatribes.javatribes.model;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@ToString
public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    public String getAuthority() {
        return name();
    }

}
