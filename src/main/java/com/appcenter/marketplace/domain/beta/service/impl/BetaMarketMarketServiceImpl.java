package com.appcenter.marketplace.domain.beta.service.impl;

import com.appcenter.marketplace.domain.beta.BetaMarket;
import com.appcenter.marketplace.domain.beta.dto.req.BetaMarketReq;
import com.appcenter.marketplace.domain.beta.dto.res.BetaMarketRes;
import com.appcenter.marketplace.domain.beta.repository.BetaMarketRepository;
import com.appcenter.marketplace.domain.beta.service.BetaMarketService;
import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
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

import static com.appcenter.marketplace.global.common.StatusCode.CATEGORY_NOT_EXIST;
import static com.appcenter.marketplace.global.common.StatusCode.FILE_SAVE_INVALID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BetaMarketMarketServiceImpl implements BetaMarketService {
    private final BetaMarketRepository betaMarketRepository;
    private final CategoryRepository categoryRepository;
    private final BetaMarketService betaMarketService;

    @Value("${image.upload.path}")
    private String uploadFolder;


    @Override
    @Transactional
    public BetaMarketRes createBetaMarket(BetaMarketReq betaMarketReq, MultipartFile multipartFile){
        Category category=findCategoryByMajor(betaMarketReq.getMajor());

        BetaMarket betaMarket=betaMarketRepository.save(betaMarketReq.toEntity(category));

        String imageFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        betaMarket.updateimage(imageFileName);

        try{
            File uploadFile = new File(uploadFolder + imageFileName);
            multipartFile.transferTo(uploadFile);
        }
        catch(IOException e){
            throw new CustomException(FILE_SAVE_INVALID);
        }

        return BetaMarketRes.of(betaMarket);
    }

    // 카테고리 조회
    private Category findCategoryByMajor(String major){
        // 카테고리 대분류명 존재 확인
        if(Major.exists(major)) {
            return categoryRepository.findByMajor(Major.valueOf(major))
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXIST));
        }
        else throw new CustomException(CATEGORY_NOT_EXIST);
    }
}
