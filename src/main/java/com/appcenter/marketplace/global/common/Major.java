package com.appcenter.marketplace.global.common;

public enum Major {
    ICOOP,FOOD,CAFE,GAME,SPORT,BEAUTY,EDUCATION,ONLINE,INTERIOR;

    public static boolean exists(String value) {
        for (Major major : values()) {
            if (major.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
