package com.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProfileTest {

    @Test
    void profile() {
        new Profile("springss");
        new Profile("asdasd1111");
        new Profile("12345");
        new Profile("");
    }
    @Test
    void profileFail() {
        assertThatThrownBy(() -> new Profile("longlonglonglonglonglonglong")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("한글입니다")).isInstanceOf(IllegalArgumentException.class);
    }
}