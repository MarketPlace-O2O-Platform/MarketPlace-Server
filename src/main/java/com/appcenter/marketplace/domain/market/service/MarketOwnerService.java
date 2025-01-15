package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MarketOwnerService {

    MarketDetailsRes createMarket(MarketReq marketReq, List<MultipartFile> multiPartFileList);

    MarketDetailsRes updateMarket(Long marketId, MarketReq marketReq);

    MarketDetailsRes updateMarketImage (Long marketId, MarketImageUpdateReq marketImageUpdateReq,
                                        List<MultipartFile> multiPartFileList);
    void deleteMarket(Long marketId);

}
