package com.splearn.adapter.integration;

import com.splearn.domain.Email;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.*;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("aaaa@ttt.aaaa"), "subject", "body");

        assertThat(out.capturedLines()[0]).isEqualTo("DummyEmailSender.send Email: Email[address=aaaa@ttt.aaaa]");
    }
}