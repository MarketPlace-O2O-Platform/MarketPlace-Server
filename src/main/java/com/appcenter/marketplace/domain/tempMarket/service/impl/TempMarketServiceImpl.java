package com.appcenter.marketplace.domain.tempMarket.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketRes;
import com.appcenter.marketplace.domain.tempMarket.repository.TempMarketRepository;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Service
@RequiredArgsConstructor
public class TempMarketServiceImpl implements TempMarketService {
    private final TempMarketRepository tempMarketRepository;
    private final CategoryRepository categoryRepository;

    @Value("${tempImage.upload.path}")
    private String imageFolder;

    @Override
    @Transactional
    public TempMarketRes create(TempMarketReq marketReq, MultipartFile multipartFile) {
        Category category = findCategory(marketReq.getCategory());

        // 중복 매장명 확인
        checkDuplicateMarket(marketReq.getMarketName());
        // 이미지 저장
        String imageName = saveImage(multipartFile);
        TempMarket market =tempMarketRepository.save(marketReq.toEntity(category, imageName));

        // 요청 매장 삭제 로직 추가 (요청 API 구현 확인 후)

        return TempMarketRes.toDto(market);
    }

    private Category findCategory(String category) {
        if (Major.exists(category)) {
            return categoryRepository.findByMajor(Major.valueOf(category))
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXIST));
        } else throw new CustomException(CATEGORY_NOT_EXIST);
    }

    private void checkDuplicateMarket(String name){
        boolean existMarket = tempMarketRepository.existsByName(name);
        if(existMarket){
            throw new CustomException(MARKET_ALREADY_EXISTS);
        }
    }

    private String saveImage(MultipartFile file) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File uploadFile = new File(imageFolder + fileName);
            file.transferTo(uploadFile);
            return fileName;
        }catch(IOException e){
            throw new CustomException(FILE_SAVE_INVALID);
        }
    }
}
