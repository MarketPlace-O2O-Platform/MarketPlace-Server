package com.appcenter.marketplace.domain.image.service;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReqDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    void createImage(Market market, List<MultipartFile> multipartFileList) throws IOException;

    void UpdateImage(Market market, MarketImageUpdateReqDto marketImageUpdateReqDto,
                     List<MultipartFile> multipartFileList) throws IOException;
}
