package com.splearn.application.member.provided;

import com.splearn.SplearnTestConfiguration;
import com.splearn.domain.member.*;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(
    MemberRegister memberRegister,
    EntityManager entityManager
) {


    @Test
    void register() {
        Member member = registerMember();

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        registerMember();
        assertThatThrownBy(() -> registerMember()).isInstanceOf(DuplicateEmailException.class);

    }

    @Test
    void activate() {
        Member member = registerMember();

        member = memberRegister.activate(member.getId());
        entityManager.flush();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();

    }


    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("sssss","ASDFGGH", "secret123"));
        checkValidation(new MemberRegisterRequest("sss@aaa.com","Cha", "secret123"));
        checkValidation(new MemberRegisterRequest("sss@aaa.com","Chaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "secret123"));
        checkValidation(new MemberRegisterRequest("sss@aaa.com","asdss", "et"));

    }

    @Test
    void deactivate() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        Member member = registerMember();

        memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();

        MemberInfoUpdateRequest request = new MemberInfoUpdateRequest("nicknameTest", "address11", "자기소개입니다.");
        member = memberRegister.updateInfo(member.getId(),request);
        entityManager.flush();
        entityManager.clear();

        assertThat(member.getDetail().getProfile().address()).isEqualTo("address11");
    }

    @Test
    void updateInfoFail() {
        Member member = registerMember();

        memberRegister.activate(member.getId());

        MemberInfoUpdateRequest request = new MemberInfoUpdateRequest("nicknameTest", "address11", "자기소개입니다.");
        memberRegister.updateInfo(member.getId(),request);

        Member member2 = registerMember("aa1@aa.com");
        memberRegister.activate(member2.getId());
        entityManager.flush();
        entityManager.clear();

        // member2는 기존의 member와 같은 profile을 사용할 수 없다
        assertThatThrownBy(() -> {
            memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("Jeus2", "address11", "Instro"));
        }).isInstanceOf(DuplicateProfileException.class);

        // 다른 profile address 로는 변경 가능
        memberRegister.updateInfo(member2.getId(), new MemberInfoUpdateRequest("Jeus2", "address12", "Instro"));

        // 기존 profile address 를 바꾸는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Jeus2", "address11", "Instro"));

        //  profile address 를 제거하는 것도 가능
        memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Jeus2", "", "Instro"));

        // profile address 중복은 허용하지 않음
        assertThatThrownBy(() -> {
            memberRegister.updateInfo(member.getId(), new MemberInfoUpdateRequest("Jeus2", "address12", "Instro"));
        }).isInstanceOf(DuplicateProfileException.class);
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid)).isInstanceOf(ConstraintViolationException.class);
    }
    private Member registerMember() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }
    private Member registerMember(String email) {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest(email));
        entityManager.flush();
        entityManager.clear();
        return member;
    }
}
