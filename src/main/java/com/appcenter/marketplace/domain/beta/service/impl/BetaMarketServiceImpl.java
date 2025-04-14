package com.appcenter.marketplace.domain.beta.service.impl;

import com.appcenter.marketplace.domain.beta.BetaCoupon;
import com.appcenter.marketplace.domain.beta.BetaMarket;
import com.appcenter.marketplace.domain.beta.dto.req.BetaMarketReq;
import com.appcenter.marketplace.domain.beta.dto.res.BetaMarketRes;
import com.appcenter.marketplace.domain.beta.repository.BetaCouponRepository;
import com.appcenter.marketplace.domain.beta.repository.BetaMarketRepository;
import com.appcenter.marketplace.domain.beta.service.BetaMarketService;
import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.member.repository.MemberRepository;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BetaMarketServiceImpl implements BetaMarketService {
    private final BetaMarketRepository betaMarketRepository;
    private final BetaCouponRepository betaCouponRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    @Value("${image.upload.path}")
    private String uploadFolder;


    @Override
    @Transactional
    public BetaMarketRes createBetaMarket(BetaMarketReq betaMarketReq, MultipartFile multipartFile){
        Category category=findCategoryByMajor(betaMarketReq.getMajor());

        BetaMarket betaMarket=betaMarketRepository.save(betaMarketReq.toEntity(category));

        String imageFileName = UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();

        betaMarket.updateImage(imageFileName);

        try{
            File uploadFile = new File(uploadFolder + imageFileName);
            multipartFile.transferTo(uploadFile);
        }
        catch(IOException e){
            throw new CustomException(FILE_SAVE_INVALID);
        }

        return BetaMarketRes.of(betaMarket);
    }

    @Override
    @Transactional
    public BetaMarketRes updateBetaMarket(Long betaMarketId, BetaMarketReq betaMarketReq, MultipartFile multiPartFile) {

        Category category = findCategoryByMajor(betaMarketReq.getMajor());

        BetaMarket betaMarket=betaMarketRepository.findById(betaMarketId).orElseThrow(
                () -> new CustomException(MARKET_NOT_EXIST));

        betaMarket.update(betaMarketReq, category);

        String imageFileName = UUID.randomUUID() + "_" + multiPartFile.getOriginalFilename();

        betaMarket.updateImage(imageFileName);

        try{
            File uploadFile = new File(uploadFolder + imageFileName);
            multiPartFile.transferTo(uploadFile);
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

    public void sendCouponsToAllMembers(BetaMarket betaMarket) {
        List<Member> memberList= memberRepository.findAll();
        List<BetaCoupon> betaCouponList = new ArrayList<>();

        for (Member member : memberList) {
            betaCouponList.add(BetaCoupon.builder()
                    .isUsed(false)
                    .member(member)
                    .betaMarket(betaMarket)
                    .build());
        }

        betaCouponRepository.saveAll(betaCouponList);
    }
}
