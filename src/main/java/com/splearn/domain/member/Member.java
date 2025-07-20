package com.splearn.domain.member;

import com.splearn.domain.AbstractEntity;
import com.splearn.domain.shared.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.springframework.util.Assert;

import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString(callSuper = true, exclude = "detail")
@Getter
public class Member extends AbstractEntity {

    @NaturalId // @NaturalId 를 사용하면 @Id와 마찬가지로 영속성컨텍스트에 캐시된다..
    private Email email;

    //@Column(length = 100, nullable = false)
    private String nickname;

    //@Column(length = 200, nullable = false)
    private String passwordHash;

    //@Enumerated
    //@Column(length = 50, nullable = false)
    private MemberStatus status;

    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberDetail detail;

    public static Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));
        member.status = MemberStatus.PENDING;

        member.detail = MemberDetail.create();

        return member;
    }
    public void activate() {
        state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");
        this.status = MemberStatus.ACTIVE;
        this.detail.activate();
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");
        this.status = MemberStatus.DEACTIVATED;
        this.detail.deactivate();

    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest) {
        Assert.state(isActive(), "등록 완료 상태가 아니면 정보를 수정할 수 없습니다.");
        this.nickname = requireNonNull(updateRequest.nickname());

        this.detail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVE;
    }
}
