package com.appcenter.marketplace.domain.requestMarket.service;

import com.appcenter.marketplace.domain.requestMarket.dto.req.RequestMarketCreateReq;
import com.appcenter.marketplace.domain.requestMarket.dto.res.RequestMarketRes;

public interface RequestMarketService {

    RequestMarketRes createRequestMarket(RequestMarketCreateReq requestMarketCreateReq);
}
