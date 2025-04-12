package com.appcenter.marketplace.domain.beta.dto.req;

import com.appcenter.marketplace.domain.beta.BetaMarket;
import com.appcenter.marketplace.domain.category.Category;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BetaMarketReq {
    @NotBlank(message = "매장명은 필수입력값입니다.")
    private String marketName;

    @NotBlank(message = "쿠폰명은 필수입력값입니다.")
    private String couponName;

    @NotBlank(message = "쿠폰 설명은 필수입력값입니다.")
    private String couponDetail;

    @NotBlank(message = "카테고리 대분류는 필수입력값입니다.")
    private String major;

    private Boolean isPromise;

    public BetaMarket toEntity(Category category){
        return BetaMarket.builder()
                .marketName(marketName)
                .couponName(couponName)
                .couponDetail(couponDetail)
                .category(category)
                .isPromise(isPromise)
                .build();
    }
}
