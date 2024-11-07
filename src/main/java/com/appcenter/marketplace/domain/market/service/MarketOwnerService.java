package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MarketOwnerService {

    MarketDetailsResDto createMarket(MarketCreateReqDto marketCreateReqDto, List<MultipartFile> multiPartFileList) throws IOException;

    MarketDetailsResDto updateMarket(Long marketId, MarketUpdateReqDto marketUpdateReqDto);

    MarketDetailsResDto updateMarketImage (Long marketId, MarketImageUpdateReqDto marketImageUpdateReqDto,
                                           List<MultipartFile> multiPartFileList) throws IOException;
    void deleteMarket(Long marketId);

}
