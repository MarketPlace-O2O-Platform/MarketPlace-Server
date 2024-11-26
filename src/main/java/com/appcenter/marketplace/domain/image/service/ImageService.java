package com.appcenter.marketplace.domain.image.service;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void createImage(Market market, List<MultipartFile> multipartFileList);

    void UpdateImage(Market market, MarketImageUpdateReqDto marketImageUpdateReqDto,
                     List<MultipartFile> multipartFileList);

    void deleteAllImages(Long marketId);
}
