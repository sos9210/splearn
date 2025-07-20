//package com.splearn.application.member.provided;
//
//import com.splearn.application.MemberService;
//import com.splearn.application.member.required.EmailSender;
//import com.splearn.application.member.required.MemberRepository;
//import com.splearn.domain.shared.Email;
//import com.splearn.domain.member.Member;
//import com.splearn.domain.member.MemberFixture;
//import com.splearn.domain.member.MemberStatus;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//
//class MemberRegisterManualTest {
//
//    @Test
//    void registerTestStub() {
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                new EmailSenderStub(),
//                MemberFixture.createPasswordEncoder()
//        );
//
//        Member member = register.register(MemberFixture.createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//    }
//    @Test
//    void registerTestMock() {
//        EmailSenderMock emailSenderMock = new EmailSenderMock();
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                emailSenderMock,
//                MemberFixture.createPasswordEncoder()
//        );
//
//        Member member = register.register(MemberFixture.createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//        assertThat(emailSenderMock.getTos()).hasSize(1);
//        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());
//    }
//    @Test
//    void registerTestMockito() {
//        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);
//        MemberRegister register = new MemberService(
//                new MemberRepositoryStub(),
//                emailSenderMock,
//                MemberFixture.createPasswordEncoder()
//        );
//
//        Member member = register.register(MemberFixture.createMemberRegisterRequest());
//
//        assertThat(member.getId()).isNotNull();
//        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
//
//        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());
//    }
//
//    static class MemberRepositoryStub implements MemberRepository {
//        @Override
//        public Member save(Member member) {
//            ReflectionTestUtils.setField(member, "id", 1L);
//            return member;
//        }
//
//        @Override
//        public Optional<Member> findByEmail(Email email) {
//            return Optional.empty();
//        }
//
//        @Override
//        public Optional<Member> findById(Long memberId) {
//            return Optional.empty();
//        }
//    }
//
//    static class EmailSenderStub implements EmailSender {
//        @Override
//        public void send(Email email, String subject, String body) {
//        }
//    }
//
//    static class EmailSenderMock implements EmailSender {
//        private List<Email> tos = new ArrayList<>();
//
//        public List<Email> getTos() {
//            return tos;
//        }
//
//        @Override
//        public void send(Email email, String subject, String body) {
//            tos.add(email);
//        }
//    }
//
//}