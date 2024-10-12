package com.appcenter.marketplace.global.common;

public enum Major {
    FOOD, DESSERT, SPORT, BEAUTY, HOSPITAL, EDUCATION, ETC;

    public static boolean exists(String value) {
        for (Major major : values()) {
            if (major.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
