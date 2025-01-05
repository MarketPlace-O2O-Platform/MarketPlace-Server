package com.appcenter.marketplace.domain.tempMarket.service;

import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketDetailRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketHiddenRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TempMarketAdminService {
    TempMarketDetailRes createMarket(TempMarketReq marketReq, MultipartFile multipartFile);

    TempMarketDetailRes updateMarket(Long marketId, TempMarketReq marketReq, MultipartFile multipartFile);

    List<TempMarketDetailRes> getMarketList();

    TempMarketHiddenRes toggleHidden(Long marketId);

    void deleteMarket(Long marketId);


}
