package com.appcenter.marketplace.domain.beta.dto.res;

import lombok.Getter;

import java.util.List;

@Getter
public class BetaCouponPageRes<T> {
    private final List<T> betaCouponResDtos;
    private final boolean hasNext;

    public BetaCouponPageRes(List<T> betaCouponResDtos, boolean hasNext){
        this.betaCouponResDtos = betaCouponResDtos;
        this.hasNext = hasNext;
    }
}
