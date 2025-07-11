package com.splearn.application.required;

import com.splearn.domain.Member;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.splearn.domain.MemberFixture.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createManager() {

        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        Assertions.assertThat(member.getId()).isNull();
        memberRepository.save(member);
        Assertions.assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }
    @Test
    void duplicateEmailFail() {

        Member member1 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());

        memberRepository.save(member1);
        Member member2 = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        Assertions.assertThatThrownBy(() -> memberRepository.save(member2)).isInstanceOf(DataIntegrityViolationException.class);

    }
}