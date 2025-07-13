package com.splearn.application;

import com.splearn.application.provided.MemberFinder;
import com.splearn.application.provided.MemberRegister;
import com.splearn.application.required.EmailSender;
import com.splearn.application.required.MemberRepository;
import com.splearn.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        /**
         * 업데이트시에도 save호출이 권장된다.
         * 1. Spring Data는 JPA만을 위한 기술이 아니다. 다양한 종류의 db저장기술을 적용하기위해 권장되는 사항이다.
         * 2. domain Event Publish, 도메인 이벤트를 발행하는 기능을 사용할때 업데이트시에도 save호출이 필수다.
         */
        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해 주세요.");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if(memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
        }
    }

}
