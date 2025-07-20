package com.splearn.adapter.webapi;

import com.splearn.adapter.webapi.dto.MemberRegisterResponse;
import com.splearn.application.member.provided.MemberRegister;
import com.splearn.domain.member.Member;
import com.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    // register api
    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);
        return MemberRegisterResponse.of(member);
    }
}
