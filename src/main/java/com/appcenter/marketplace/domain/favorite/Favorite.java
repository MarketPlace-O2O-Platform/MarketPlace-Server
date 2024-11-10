package com.appcenter.marketplace.domain.favorite;

import com.appcenter.marketplace.domain.market.Market;
import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "favorite")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false)
    private Market market;

    @Builder
    public Favorite(Boolean isDeleted, Member member, Market market) {
        this.isDeleted = isDeleted;
        this.member = member;
        this.market = market;
    }

    public void toggleIsDeleted(){ this.isDeleted= !isDeleted; }
}
