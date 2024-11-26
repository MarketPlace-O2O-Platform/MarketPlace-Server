package com.appcenter.marketplace.domain.market.dto.req;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.market.Market;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MarketCreateReq {
    @NotBlank(message = "매장명은 필수입력값입니다.")
    private String marketName;

    @NotBlank(message = "매장에 대한 설명은 필수입력값입니다.")
    private String description;

    private String operationHours;

    private String closedDays;

    private String phoneNumber;

    @NotBlank(message = "주소값은 필수입력값입니다.")
    private String address;

    @NotBlank(message = "카테고리 대분류는 필수입력값입니다.")
    private String major;

    @Builder
    public MarketCreateReq(String marketName, String description, String operationHours, String closedDays, String phoneNumber, String address) {
        this.marketName = marketName;
        this.description = description;
        this.operationHours = operationHours;
        this.closedDays = closedDays;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Market toEntity(Category category, Local local){
        return Market.builder()
                .name(marketName)
                .description(description)
                .operationHours(operationHours)
                .closedDays(closedDays)
                .phoneNumber(phoneNumber)
                .address(address)
                .category(category)
                .local(local)
                .build();
    }
}
