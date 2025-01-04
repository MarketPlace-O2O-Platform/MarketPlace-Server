package com.appcenter.marketplace.domain.member.repository;

import com.appcenter.marketplace.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

}
