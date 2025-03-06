package com.appcenter.marketplace.domain.beta.service;

import com.appcenter.marketplace.domain.beta.dto.req.BetaMarketReq;
import com.appcenter.marketplace.domain.beta.dto.res.BetaMarketRes;
import org.springframework.web.multipart.MultipartFile;

public interface BetaMarketService {

    BetaMarketRes createBetaMarket(BetaMarketReq betaMarketReq, MultipartFile multiPartFile);
}
