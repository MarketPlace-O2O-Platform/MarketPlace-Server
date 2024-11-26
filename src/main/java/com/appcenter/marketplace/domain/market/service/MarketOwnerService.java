package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MarketOwnerService {

    MarketDetailsRes createMarket(MarketCreateReq marketCreateReq, List<MultipartFile> multiPartFileList);

    MarketDetailsRes updateMarket(Long marketId, MarketUpdateReq marketUpdateReq);

    MarketDetailsRes updateMarketImage (Long marketId, MarketImageUpdateReq marketImageUpdateReq,
                                        List<MultipartFile> multiPartFileList);
    void deleteMarket(Long marketId);

}
