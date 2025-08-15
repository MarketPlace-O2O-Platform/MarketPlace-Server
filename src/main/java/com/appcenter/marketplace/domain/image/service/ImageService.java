package com.appcenter.marketplace.domain.image.service;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void createImage(Market market, List<MultipartFile> multipartFileList);

    void updateImage(Market market, MarketImageUpdateReq marketImageUpdateReq,
                     List<MultipartFile> multipartFileList);

    void hardDeleteAllImages(Long marketId);
    void softDeleteImage(Long marketId);
}
