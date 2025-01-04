package com.appcenter.marketplace.domain.tempMarket.service;

import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketHiddenRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketDetailRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketPageRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TempMarketService {
    TempMarketDetailRes create(TempMarketReq marketReq, MultipartFile multipartFile);

    TempMarketDetailRes updateMarket(Long marketId, TempMarketReq marketReq, MultipartFile multipartFile);

    TempMarketHiddenRes toggleHidden(Long marketId);

    TempMarketPageRes<TempMarketRes> getMarketList(Long memberId, Long marketId, Integer size, String category);

    TempMarketPageRes<TempMarketRes> getUpcomingNearMarketList(Long memberId, Long marketId, Long cheerCount, Integer size);
    void deleteMarket(Long marketId);
}