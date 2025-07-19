package com.splearn.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

//@Embeddable
public record Email(/*@Column(name = "email_address",length = 150, nullable = false) */String address) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");

    public Email {
        if(!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid email " + address);
        }

    }
}
