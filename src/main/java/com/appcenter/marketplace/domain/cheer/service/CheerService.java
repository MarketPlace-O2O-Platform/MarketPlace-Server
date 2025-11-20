package com.appcenter.marketplace.domain.cheer.service;

import com.appcenter.marketplace.domain.cheer.dto.CheerCountRes;

public interface CheerService {
    CheerCountRes cheerTempMarket(Long memberId, Long marketId);
}
