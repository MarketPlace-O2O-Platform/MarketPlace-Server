package com.appcenter.marketplace.domain.image.service.impl;

import com.appcenter.marketplace.domain.image.Image;
import com.appcenter.marketplace.domain.image.ImageRepository;
import com.appcenter.marketplace.domain.image.service.ImageService;
import com.appcenter.marketplace.domain.market.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Value("${image.upload.path}")
    private String uploadFolder;

    // 이미지 리스트의 첫번째 요소를 썸네일로 설정
    @Override
    @Transactional
    public void createImage(Market market, List<MultipartFile> multipartFileList) throws IOException {

        for (int i = 0; i < multipartFileList.size(); i++){
            MultipartFile file = multipartFileList.get(i);
            String imageFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File uploadFile = new File(uploadFolder + imageFileName);

            file.transferTo(uploadFile);

            Image image= Image.builder()
                    .name(imageFileName)
                    .order(i+1)
                    .market(market)
                    .build();
            imageRepository.save(image);

            // 첫번 째 요소는 썸네일로 지정
            if(i==0){
                market.updateThumbnailPath(image.getName());
            }

        }
    }
}
