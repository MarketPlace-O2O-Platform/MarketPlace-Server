package com.appcenter.marketplace.global.jwt;

import com.appcenter.marketplace.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Member member;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = member.getRole().toString();
        log.info("CustomUserDetails.getAuthorities: memberId={}, role={}", member.getId(), role);
        return List.of(new SimpleGrantedAuthority(role));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return "";
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return member.getId().toString();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
