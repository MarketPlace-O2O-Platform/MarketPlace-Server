package com.appcenter.marketplace.domain.tempMarket.service;

import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketDetailRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketHiddenRes;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface TempMarketAdminService {
    TempMarketDetailRes createMarket(TempMarketReq marketReq, MultipartFile multipartFile);

    TempMarketDetailRes updateMarket(Long marketId, TempMarketReq marketReq, MultipartFile multipartFile);

    Page<TempMarketDetailRes> getMarketList(Integer page, Integer size);

    TempMarketDetailRes getMarket(Long marketId);

    TempMarketHiddenRes toggleHidden(Long marketId);

    void hardDeleteMarket(Long marketId);


}
