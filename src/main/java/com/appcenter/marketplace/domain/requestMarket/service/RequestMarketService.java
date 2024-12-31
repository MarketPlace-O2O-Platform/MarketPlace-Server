package com.appcenter.marketplace.domain.requestMarket.service;

import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;

public interface RequestMarketService {

    RequestMarketRes createRequestMarket(RequestMarketReq requestMarketReq);
}
