package com.splearn.application.member.provided;

import com.splearn.domain.member.Member;
import com.splearn.domain.member.MemberInfoUpdateRequest;
import com.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);
    Member activate(Long memberId);
    Member deactivate(Long memberId);
    Member updateInfo(Long MemberId, @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);
}
