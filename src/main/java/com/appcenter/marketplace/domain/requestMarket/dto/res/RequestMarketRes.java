package com.appcenter.marketplace.domain.requestMarket.dto.res;

import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestMarketRes {

    private final Long id;
    private final String name;
    private final String address;
    private final Integer count;

    @QueryProjection
    @Builder
    public RequestMarketRes(Long id, String name, String address, Integer count) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.count = count;
    }

    public static RequestMarketRes from(RequestMarket requestMarket){
        return RequestMarketRes.builder()
                .id(requestMarket.getId())
                .name(requestMarket.getName())
                .address(requestMarket.getAddress())
                .count(requestMarket.getCount())
                .build();
    }
}
