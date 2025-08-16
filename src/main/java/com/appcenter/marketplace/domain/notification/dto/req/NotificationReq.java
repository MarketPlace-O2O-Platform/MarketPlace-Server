package com.appcenter.marketplace.domain.notification.dto.req;


import com.appcenter.marketplace.domain.member.Member;
import com.appcenter.marketplace.domain.notification.Notification;
import com.appcenter.marketplace.global.common.TargetType;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReq {

    @NotBlank(message = "제목은 필수입력값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입력값입니다.")
    private String body;

    private Long targetId;

    private TargetType targetType;

    @Builder
    public NotificationReq(String title, String body, Long targetId, TargetType targetType) {
        this.title = title;
        this.body = body;
        this.targetId = targetId;
        this.targetType = targetType;
    }

    public Notification toEntity(Member member){
        return Notification.builder()
                .title(title)
                .body(body)
                .targetId(targetId)
                .targetType(targetType)
                .isRead(false)
                .member(member)
                .build();
    }
}
