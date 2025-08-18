package com.appcenter.marketplace.domain.notification;


import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(name = "target_id",nullable = true)
    private Long targetId;

    @Column(name = "target_type", nullable = false)
    private TargetType targetType;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Notification(String title, String body, Long targetId, TargetType targetType, Boolean isRead, Member member) {
        this.title = title;
        this.body = body;
        this.targetId = targetId;
        this.targetType = targetType;
        this.isRead = isRead;
        this.member = member;
    }

    public void setIsReadTrue() { this.isRead = true; }

}
