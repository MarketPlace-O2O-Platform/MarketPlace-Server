package com.appcenter.marketplace.domain.beta.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BetaMarketService {

    MarketDetailsRes createBetaMarket(MarketReq marketReq, List<MultipartFile> multiPartFileList);
}
