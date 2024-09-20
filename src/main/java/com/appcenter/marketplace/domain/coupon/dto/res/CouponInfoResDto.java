package com.appcenter.marketplace.domain.coupon.dto.res;

import com.appcenter.marketplace.domain.coupon.domain.Coupon;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponInfoResDto {
    private final Long couponId;
    private final String couponName;
    private final String description;
    private final LocalDateTime deadLine;
    private final int stock;
    private final boolean isHidden;
    private final LocalDateTime createdAt;

    public static CouponInfoResDto toDto(Long couponId, String couponName, String description, LocalDateTime deadLine, int stock, boolean isHidden, LocalDateTime createdAt){
        return new CouponInfoResDto(couponId, couponName, description, deadLine, stock, isHidden, createdAt);
    }

}
