package com.splearn.application.member.provided;

import com.splearn.domain.member.Member;

/**
 * 회원을 조회한다.
 */
public interface MemberFinder {
    Member find(Long memberId);
}
