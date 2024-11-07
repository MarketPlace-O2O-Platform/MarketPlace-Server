package com.appcenter.marketplace.domain.market.dto.res;

import com.appcenter.marketplace.domain.image.dto.res.ImageResDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.List;

@Getter
public class MarketDetailResDto {
    private final Long marketId;
    private final String name;
    private final String description;
    private final String operationHours;
    private final String closedDays;
    private final String phoneNumber;
    private final String address;
    private final List<ImageResDto> imageResDtoList;

    @QueryProjection
    public MarketDetailResDto(Long marketId, String name, String description, String operationHours, String closedDays, String phoneNumber, String address, List<ImageResDto> imageResDtoList) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.operationHours = operationHours;
        this.closedDays = closedDays;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.imageResDtoList = imageResDtoList;
    }
}
