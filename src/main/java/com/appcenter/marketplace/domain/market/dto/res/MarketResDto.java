package com.appcenter.marketplace.domain.market.dto.res;

import com.appcenter.marketplace.domain.market.Market;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MarketResDto {
    private final Long marketId;
    private final String name;
    private final String description;
    private final String operationHours;
    private final String closedDays;
    private final String phoneNumber;
    private final String address;
    private final String thumbnail;

    @Builder
    public MarketResDto(Long marketId, String name, String description, String operationHours, String closedDays, String phoneNumber, String address, String thumbnail) {
        this.marketId = marketId;
        this.name = name;
        this.description = description;
        this.operationHours = operationHours;
        this.closedDays = closedDays;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.thumbnail = thumbnail;
    }

    public static MarketResDto from(Market market){
        return MarketResDto.builder()
                .marketId(market.getId())
                .name(market.getName())
                .description(market.getDescription())
                .operationHours(market.getOperationHours())
                .closedDays(market.getClosedDays())
                .phoneNumber(market.getPhoneNumber())
                .address(market.getAddress())
                .thumbnail(market.getThumbnail())
                .build();
    }
}
