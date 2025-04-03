package com.appcenter.marketplace.global.fcm.event;

import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.market.Market;
import lombok.Getter;

@Getter
public class SendNewCouponFcmEvent {
    private final Market market;
    private final Coupon coupon;

    public SendNewCouponFcmEvent(Market market, Coupon coupon) {
        this.market = market;
        this.coupon = coupon;
    }
}
