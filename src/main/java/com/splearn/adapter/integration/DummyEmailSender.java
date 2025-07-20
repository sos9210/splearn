package com.splearn.adapter.integration;

import com.splearn.application.member.required.EmailSender;
import com.splearn.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender.send Email: " + email);
    }
}
