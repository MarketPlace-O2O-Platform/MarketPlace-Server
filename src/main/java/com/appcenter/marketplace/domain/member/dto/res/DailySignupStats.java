package com.appcenter.marketplace.domain.member.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailySignupStats {
    private final LocalDate date;
    private final Long signupCount;

    @Builder
    public DailySignupStats(LocalDate date, Long signupCount) {
        this.date = date;
        this.signupCount = signupCount;
    }

    public static DailySignupStats of(LocalDate date, Long signupCount) {
        return DailySignupStats.builder()
                .date(date)
                .signupCount(signupCount)
                .build();
    }
}
