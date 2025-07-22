package com.learningtest.archunit.application;

import com.learningtest.archunit.domain.MyMember;

public class MyService {
    MyService2 myService;
    MyMember myMember;

    void run() {
        myService = new MyService2();
        myMember = new MyMember();
        System.out.println(myService);
        System.out.println(myMember);
    }
}
