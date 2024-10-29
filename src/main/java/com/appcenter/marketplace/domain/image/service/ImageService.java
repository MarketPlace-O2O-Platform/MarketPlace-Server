package com.appcenter.marketplace.domain.image.service;

import com.appcenter.marketplace.domain.market.Market;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    void createImage(Market market, List<MultipartFile> multipartFileList) throws IOException;
}
