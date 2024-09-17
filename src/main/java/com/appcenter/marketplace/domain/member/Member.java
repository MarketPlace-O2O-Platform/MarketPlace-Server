package com.appcenter.marketplace.domain.member;

import com.appcenter.marketplace.global.common.BaseEntity;
import jakarta.persistence.Access;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    private Long id;

}
