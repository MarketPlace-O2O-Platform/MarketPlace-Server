package com.appcenter.marketplace.domain.market.service.impl;

import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.category.CategoryRepository;
import com.appcenter.marketplace.domain.coupon.Coupon;
import com.appcenter.marketplace.domain.coupon.service.CouponOwnerService;
import com.appcenter.marketplace.domain.image.service.ImageService;
import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.local.repository.LocalRepository;
import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.market.dto.req.MarketImageUpdateReq;
import com.appcenter.marketplace.domain.market.dto.req.MarketOrderItem;
import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.domain.market.dto.res.MarketDetailsRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketPageRes;
import com.appcenter.marketplace.domain.market.dto.res.MarketRes;
import com.appcenter.marketplace.domain.market.repository.MarketRepository;
import com.appcenter.marketplace.domain.market.service.AdminMarketService;
import com.appcenter.marketplace.domain.market.service.MarketService;
import com.appcenter.marketplace.domain.member_coupon.service.MemberCouponService;
import com.appcenter.marketplace.domain.member_payback.service.MemberPaybackService;
import com.appcenter.marketplace.domain.payback.service.PaybackService;
import com.appcenter.marketplace.global.common.Major;
import com.appcenter.marketplace.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.appcenter.marketplace.domain.local.QLocal.local;
import static com.appcenter.marketplace.global.common.StatusCode.*;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class AdminMarketServiceImpl implements AdminMarketService {
    private final MarketRepository marketRepository;
    private final CategoryRepository categoryRepository;
    private final LocalRepository localRepository;
    private final MarketService marketService;
    private final ImageService imageService;
    private final CouponOwnerService couponOwnerService;
    private final MemberCouponService memberCouponService;
    private final PaybackService paybackService;
    private final MemberPaybackService memberPaybackService;

    @Override
    public MarketPageRes<MarketRes> getAllMarkets(Long marketId, Integer size, String major) {
        List<MarketRes> marketResList;

        // marketId를 orderNo로 변환 (marketId가 null이 아닌 경우)
        Integer lastOrderNo = null;
        if (marketId != null) {
            Market market = marketRepository.findById(marketId)
                    .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
            lastOrderNo = market.getOrderNo();
        }

        if (major == null) {
            marketResList = marketRepository.findMarketListForAdmin(lastOrderNo, size);
        } else if (Major.exists(major)) {
            marketResList = marketRepository.findMarketListByCategoryForAdmin(lastOrderNo, size, major);
        } else throw new CustomException(CATEGORY_NOT_EXIST);

        return checkNextPageAndReturn(marketResList, size);
    }

    @Override
    public MarketDetailsRes getMarketDetails(Long marketId) {
        return marketService.getMarketDetails(marketId);
    }

    @Override
    @Transactional
    public MarketDetailsRes createMarket(MarketReq marketReq, List<MultipartFile> multipartFileList) {
        Category category = findCategoryByMajor(marketReq.getMajor());

        StringTokenizer st = new StringTokenizer(marketReq.getAddress());

        if (st.countTokens() < 2) {
            throw new CustomException(ADDRESS_INVALID);
        }

        Local local = localRepository.findByAdress(st.nextToken(), st.nextToken());

        // 순서 자동 등록: orderNo가 null이면 마지막 번호 + 1 할당
        Integer orderNo = marketReq.getOrderNo();
        if (orderNo == null) {
            orderNo = marketRepository.findMaxOrderNo().orElse(0) + 1;
        }

        Market market = Market.builder()
                .name(marketReq.getMarketName())
                .description(marketReq.getDescription())
                .operationHours(marketReq.getOperationHours())
                .closedDays(marketReq.getClosedDays())
                .phoneNumber(marketReq.getPhoneNumber())
                .address(marketReq.getAddress())
                .category(category)
                .local(local)
                .isDeleted(false)
                .orderNo(orderNo)
                .build();

        marketRepository.save(market);
        imageService.createImage(market, multipartFileList);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsRes updateMarket(Long marketId, MarketReq marketReq) {
        Market market = findMarketByMarketId(marketId);
        Category category = findCategoryByMajor(marketReq.getMajor());
        market.updateMarketInfo(marketReq, category);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public MarketDetailsRes updateMarketImage(Long marketId, MarketImageUpdateReq marketImageUpdateReq, List<MultipartFile> multipartFileList) {
        Market market = findMarketByMarketId(marketId);
        imageService.updateImage(market, marketImageUpdateReq, multipartFileList);
        return marketService.getMarketDetails(market.getId());
    }

    @Override
    @Transactional
    public void updateMarketOrder(List<MarketOrderItem> orders) {
        // 전체 수정 변경
        orders.forEach(orderItem -> {
            Market market = findMarketByMarketId(orderItem.getMarketId());
            market.updateOrderNo(orderItem.getOrderNo());
        });
    }

    @Override
    @Transactional
    public void deleteMarket(Long marketId) {
        Market market = findMarketByMarketId(marketId);

        List<Coupon> couponList = couponOwnerService.getCoupons(market.getId());
        List<Long> couponIds = new ArrayList<>();
        for (Coupon coupon : couponList) {
            couponIds.add(coupon.getId());
        }

        memberCouponService.hardDeleteCoupon(couponIds);
        couponOwnerService.hardDeleteCoupon(market.getId());
        memberPaybackService.hardDeletePaybacksByMarket(market.getId());
        paybackService.hardDeleteCouponsByMarket(market.getId());
        imageService.softDeleteImage(marketId);
        market.softDeleteMarket();
    }

    private Category findCategoryByMajor(String major) {
        if (Major.exists(major)) {
            return categoryRepository.findByMajor(Major.valueOf(major))
                    .orElseThrow(() -> new CustomException(CATEGORY_NOT_EXIST));
        } else throw new CustomException(CATEGORY_NOT_EXIST);
    }

    private Market findMarketByMarketId(Long marketId) {
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CustomException(MARKET_NOT_EXIST));
    }

    private <T> MarketPageRes<T> checkNextPageAndReturn(List<T> marketResList, Integer size) {
        boolean hasNext = false;

        if (marketResList.size() > size) {
            hasNext = true;
            marketResList.remove(size.intValue());
        }

        return new MarketPageRes<>(marketResList, hasNext);
    }
}