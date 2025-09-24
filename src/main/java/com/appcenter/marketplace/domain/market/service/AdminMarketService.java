package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AdminMarketService {

    MarketPageRes<MarketRes> getAllMarkets(Long marketId, Integer size, String major);

    MarketDetailsRes getMarketDetails(Long marketId);

    MarketDetailsRes createMarket(MarketReq marketReq, List<MultipartFile> multipartFileList);

    MarketDetailsRes updateMarket(Long marketId, MarketReq marketReq);

    MarketDetailsRes updateMarketImage(Long marketId, MarketImageUpdateReq marketImageUpdateReq, List<MultipartFile> multipartFileList);

    void deleteMarket(Long marketId);
}