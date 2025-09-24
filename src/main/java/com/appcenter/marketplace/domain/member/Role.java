package com.appcenter.marketplace.domain.member;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public static boolean exists(String value) {
        for (Role role : values()) {
            if (role.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
