package com.appcenter.marketplace.domain.image.service.impl;

import com.appcenter.marketplace.domain.image.Image;
import com.appcenter.marketplace.domain.image.ImageRepository;
import com.appcenter.marketplace.domain.image.service.ImageService;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReqDto;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.appcenter.marketplace.global.common.StatusCode.FILE_DELETE_INVALID;
import static com.appcenter.marketplace.global.common.StatusCode.IMAGE_NOT_EXIST;

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

        // 이미지 리스트를 순회하며 저장하고 순서를 1부터 매겨 이미지 엔티티를 생성한다.
        for (int i = 0; i < multipartFileList.size(); i++) {
            MultipartFile file = multipartFileList.get(i);
            String imageFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File uploadFile = new File(uploadFolder + imageFileName);

            file.transferTo(uploadFile);

            Image image = Image.builder()
                    .sequence(i + 1)
                    .name(imageFileName)
                    .market(market)
                    .build();
            imageRepository.save(image);

            // 첫번 째 요소는 썸네일로 지정
            if (i == 0) {
                market.updateThumbnailPath(image.getName());
            }

        }
    }

    @Override
    @Transactional
    public void UpdateImage(Market market, MarketImageUpdateReqDto marketImageUpdateReqDto, List<MultipartFile> multipartFileList) throws IOException {

        // 삭제할 이미지 id 리스트를 순회하며 파일을 삭제하고 이미지 엔티티를 삭제한다.
        for (Long id : marketImageUpdateReqDto.getDeletedImageIds()) {
            Image image = findById(id);

            File file = new File(uploadFolder + image.getName());
            if (!file.delete())
                throw new CustomException(FILE_DELETE_INVALID);

            imageRepository.deleteById(id);
        }

        // 순서가 변경될 Map 객체를 순회하며 이미지 엔티티의 순서를 변경한다.
        for (Map.Entry<Long, Integer> entry : marketImageUpdateReqDto.getChangedOrders().entrySet()) {
            Long id = entry.getKey();
            Integer order = entry.getValue();

            Image image = findById(id);
            image.updateSequence(order);

            // 순서가 1인 엔티티는 마켓의 썸네일로 선정한다.
            if(order==1) market.updateThumbnailPath(image.getName());
        }

        // 추가될 이미지 리스트와 추가될 이미지의 순서 리스트의 길이가 맞지 않으면 안된다.
        if(multipartFileList.size()==marketImageUpdateReqDto.getAddedImageOrders().size()){
            // 리스트를 순회하며 이미지를 저장하고 순서를 매겨 엔티티를 생성한다.
            for (int i = 0; i < multipartFileList.size(); i++) {
                Integer order= marketImageUpdateReqDto.getAddedImageOrders().get(i);
                MultipartFile file = multipartFileList.get(i);
                String imageFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                File uploadFile = new File(uploadFolder + imageFileName);

                file.transferTo(uploadFile);

                Image image = Image.builder()
                        .sequence(order)
                        .name(imageFileName)
                        .market(market)
                        .build();
                imageRepository.save(image);

                // 순서가 1인 엔티티는 마켓의 썸네일로 선정한다.
                if(order==1) market.updateThumbnailPath(image.getName());
            }
        }

    }

    @Override
    @Transactional
    public void deleteAllImages(Long marketId) {
        List<Image> images= imageRepository.findAllByMarket_Id(marketId);

        for (Image image: images){
            File file = new File(uploadFolder + image.getName());
            if (!file.delete())
                throw new CustomException(FILE_DELETE_INVALID);
        }

        // delete 쿼리 한꺼번에 실행
        imageRepository.deleteAllInBatch(images);

    }

    private Image findById(Long id){
        return imageRepository.findById(id).orElseThrow(() -> new CustomException(IMAGE_NOT_EXIST));
    }

}
