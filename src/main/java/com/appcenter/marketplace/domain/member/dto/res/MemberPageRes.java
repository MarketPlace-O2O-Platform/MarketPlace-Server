package com.appcenter.marketplace.domain.member.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MemberPageRes<T> {
    private List<T> members;
    private boolean hasNext;
}