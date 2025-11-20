package com.appcenter.marketplace.domain.tempMarket.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.requestMarket.RequestMarket;
import com.appcenter.marketplace.domain.requestMarket.service.RequestMarketService;
import com.appcenter.marketplace.domain.tempMarket.TempMarket;
import com.appcenter.marketplace.domain.tempMarket.dto.req.TempMarketReq;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketDetailRes;
import com.appcenter.marketplace.domain.tempMarket.dto.res.TempMarketHiddenRes;
import com.appcenter.marketplace.domain.tempMarket.repository.TempMarketRepository;
import com.appcenter.marketplace.domain.tempMarket.service.TempMarketAdminService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static com.appcenter.marketplace.global.common.StatusCode.*;
import static com.appcenter.marketplace.global.common.StatusCode.FILE_DELETE_INVALID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TempMarketAdminServiceImpl implements TempMarketAdminService {
    private final TempMarketRepository tempMarketRepository;
    private final CategoryRepository categoryRepository;

    private final RequestMarketService requestMarketService;

    @Value("${tempImage.upload.path}")
    private String imageFolder;

    @Override
    @Transactional
    public TempMarketDetailRes createMarket(TempMarketReq marketReq, MultipartFile multipartFile) {
        Category category = findCategory(marketReq.getCategory());

        // 중복 매장명 확인
        checkDuplicateMarket(marketReq.getMarketName());
        // 이미지 저장
        String imageName = saveImage(multipartFile);
        TempMarket market =tempMarketRepository.save(marketReq.toEntity(category, imageName));

        // 요청 매장 삭제 로직
        // 요청 매장의 이름(unique)가 일치하는 매장만 삭제
        if(requestMarketService.existRequestMarket(market.getName())){
            RequestMarket requestMarket = requestMarketService.getRequestMarketName(market.getName());
            requestMarketService.hardDeleteRequestMarket(requestMarket.getId());
        }

        return TempMarketDetailRes.toDto(market);
    }

    @Override
    @Transactional
    public TempMarketDetailRes updateMarket(Long marketId, TempMarketReq marketReq, MultipartFile multipartFile) {
        TempMarket tempMarket = findMarket(marketId);
        Category category = findCategory(marketReq.getCategory());
        tempMarket.updateMarket(marketReq, category);

        // 이미지 수정
        if( multipartFile != null && !multipartFile.isEmpty()){
            String oldThumbnail = tempMarket.getThumbnail();
            if( oldThumbnail != null ){
                deleteImage(oldThumbnail);
            }

            String newThumbnail = saveImage(multipartFile);
            tempMarket.updateThumbnail(newThumbnail);
        }

        return TempMarketDetailRes.toDto(tempMarket);
    }

    @Override
    public Page<TempMarketDetailRes> getMarketList(Integer page, Integer size, Long categoryId) {
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page-1, size, sort);

        Page<TempMarket> tempMarketPage;
        if (categoryId != null) {
            // 카테고리 필터링
            tempMarketPage = tempMarketRepository.findByCategoryId(categoryId, pageable);
        } else {
            // 전체 조회
            tempMarketPage = tempMarketRepository.findAll(pageable);
        }

        return tempMarketPage.map(TempMarketDetailRes::toDto);
    }

    @Override
    public TempMarketDetailRes getMarket(Long marketId) {
        TempMarket tempMarket = findMarket(marketId);
        return TempMarketDetailRes.toDto(tempMarket);
    }

    @Override
    @Transactional
    public TempMarketHiddenRes toggleHidden(Long marketId) {
        TempMarket tempMarket = findMarket(marketId);
        tempMarket.toggleHidden();
        return TempMarketHiddenRes.toDto(tempMarket);
    }

    @Override
    @Transactional
    public void hardDeleteMarket(Long marketId) {
        TempMarket tempMarket = findMarket(marketId);
        tempMarketRepository.deleteById(tempMarket.getId());
    }

    private Category findCategory(String category) {
        if (Major.exists(category)) {
            return categoryRepository.findByMajor(Major.valueOf(category))
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXIST));
        } else throw new CustomException(CATEGORY_NOT_EXIST);
    }

    private TempMarket findMarket(Long marketId) {
        return tempMarketRepository.findById(marketId)
                .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
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

    private void deleteImage(String imageName){
        File file = new File(imageFolder + imageName);
        if(file.exists() && file.isFile()) {
            if (!file.delete()) {
                throw new CustomException(FILE_DELETE_INVALID);
            }
        }
    }

}
