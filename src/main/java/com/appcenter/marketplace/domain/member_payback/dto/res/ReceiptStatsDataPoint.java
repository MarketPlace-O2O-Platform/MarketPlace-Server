package com.appcenter.marketplace.domain.member_payback.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReceiptStatsDataPoint {
    private final String label;
    private final Long count;
}
