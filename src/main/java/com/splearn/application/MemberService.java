package com.splearn.application;

import com.splearn.application.provided.MemberRegister;
import com.splearn.application.required.EmailSender;
import com.splearn.application.required.MemberRepository;
import com.splearn.domain.Member;
import com.splearn.domain.MemberRegisterRequest;
import com.splearn.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check

        Member member = Member.register(registerRequest, passwordEncoder);
        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해 주세요.");

        return member;
    }
}
