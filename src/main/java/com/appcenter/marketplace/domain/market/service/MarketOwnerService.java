package com.appcenter.marketplace.domain.market.service;

import com.appcenter.marketplace.domain.market.dto.req.MarketCreateReqDto;
import com.appcenter.marketplace.domain.market.dto.req.MarketUpdateReqDto;
import com.appcenter.marketplace.domain.market.dto.res.MarketResDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MarketOwnerService {

    MarketResDto createMarket(MarketCreateReqDto marketCreateReqDto, List<MultipartFile> multiPartFileList) throws IOException;

    MarketResDto updateMarket(MarketUpdateReqDto marketUpdateReqDto, Long marketId);

    MarketResDto getMarket(Long marketId);

    void deleteMarket(Long marketId);

}
