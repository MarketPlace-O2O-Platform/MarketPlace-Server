package com.appcenter.marketplace.domain.market.dto.res;

import com.appcenter.marketplace.domain.image.dto.res.ImageRes;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class MarketDetailsRes {
    private final Long marketId;
    private final String name;
    private final String description;
    private final String operationHours;
    private final String closedDays;
    private final String phoneNumber;
    private final String address;
    private final List<ImageRes> imageResList;

    @QueryProjection
    public MarketDetailsRes(Long marketId, String name, String description, String operationHours, String closedDays, String phoneNumber, String address, List<ImageRes> imageResList) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.operationHours = operationHours;
        this.closedDays = closedDays;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.imageResList = imageResList;
    }
}
