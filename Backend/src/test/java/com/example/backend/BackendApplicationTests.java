package com.example.backend;

import com.example.backend.utils.utilClasses.IsEntityExists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private IsEntityExists isEntityExists;

    @Test
    void test() {
        System.out.println(isEntityExists.isUserExists(1));
    }

}