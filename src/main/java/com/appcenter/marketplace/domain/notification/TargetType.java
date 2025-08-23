package com.appcenter.marketplace.domain.notification;

public enum TargetType {
    MARKET, COUPON;

    public static boolean exists(String value) {
        for (TargetType type : values()) {
            if (type.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
