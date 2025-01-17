package com.appcenter.marketplace.domain.market;


import com.appcenter.marketplace.domain.category.Category;
import com.appcenter.marketplace.domain.local.Local;
import com.appcenter.marketplace.domain.market.dto.req.MarketReq;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name ="market")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = true)
    private String description;

    @Column(name = "operation_hours", nullable = true)
    private String operationHours;

    @Column(name = "closed_days", nullable = true)
    private String closedDays;

    @Column(name = "phone_number", nullable = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = true)
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_government_id", nullable = false)
    private Local local;

    @Builder
    public Market(String name, String description, String operationHours, String closedDays, String phoneNumber, String address, String thumbnail, Category category, Local local) {
        this.name = name;
        this.description = description;
        this.operationHours = operationHours;
        this.closedDays = closedDays;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.thumbnail = thumbnail;
        this.category = category;
        this.local=local;
    }

    public void updateMarketInfo(MarketReq marketReq, Category category){
        this.name = marketReq.getMarketName();
        this.description = marketReq.getDescription();
        this.operationHours = marketReq.getOperationHours();
        this.closedDays = marketReq.getClosedDays();
        this.phoneNumber = marketReq.getPhoneNumber();
        this.address = marketReq.getAddress();
        this.category= category;
    }
    public void updateThumbnailPath(String thumbnail){
        this.thumbnail= thumbnail;
    }
}
