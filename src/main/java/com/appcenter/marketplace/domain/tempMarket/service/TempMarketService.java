package com.appcenter.marketplace.domain.tempMarket.service;

import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import org.springframework.web.multipart.MultipartFile;

public interface TempMarketService {
    TempMarketRes create(TempMarketReq marketReq, MultipartFile multipartFile);
}