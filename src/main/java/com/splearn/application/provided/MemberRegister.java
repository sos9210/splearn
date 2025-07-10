package com.splearn.application.provided;

import com.splearn.domain.Member;

/**
 * 회원의 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(MemberRegister registerRequest);
}
