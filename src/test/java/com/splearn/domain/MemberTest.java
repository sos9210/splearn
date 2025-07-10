package com.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        member = Member.create(new MemberCreateRequest("test@naver.com", "Tester", "secret"), passwordEncoder);

    }

    @Test
    void createMember() {

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

    }
    @Test
    void activate() {

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }
    @Test
    void activateFail() {

        member.activate();

        assertThatThrownBy(() -> member.activate()).isInstanceOf(IllegalStateException.class);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deactivate();

        assertThatThrownBy(() -> member.deactivate()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void cgangeNickname() {
        assertThat(member.getNickname()).isEqualTo("Tester");

        member.changeNickname("Charlie");

        assertThat(member.getNickname()).isEqualTo("Charlie");
    }
    @Test
    void changePassword() {
        member.changePassword("secretChange",passwordEncoder);
        assertThat(member.verifyPassword("secretChange", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();
        assertThat(member.isActive()).isTrue();

        member.deactivate();
        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
            Member.create(new MemberCreateRequest("test.com", "Tester", "secret"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.create(new MemberCreateRequest("test@naver.com", "Tester", "secret"), passwordEncoder);
    }
}