package com.splearn.domain;

public record MemberCreateRequest(
        String email,
        String nickname,
        String password
) {

}
