package com.splearn;

import com.splearn.application.member.required.EmailSender;
import com.splearn.domain.member.MemberFixture;
import com.splearn.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * EmailSender와 PasswordEncoder는 구현되지 않았기때문에..
 * 나중에 구현되면 삭제해도 된다.
 */
@TestConfiguration
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("Sending Email : " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
