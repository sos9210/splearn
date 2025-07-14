package com.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.*;

class SplearnApplicationTest {

    @Test
    void run() {

        try (MockedStatic<SpringApplication> mock = mockStatic(SpringApplication.class)) {
            SplearnApplication.main(new String[0]);

            mock.verify(() -> SpringApplication.run(SplearnApplication.class, new String[0]));
        }
    }
}