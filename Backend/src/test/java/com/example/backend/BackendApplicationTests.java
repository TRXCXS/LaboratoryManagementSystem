package com.example.backend;

import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.service.user.UserService;
import com.example.backend.utils.enumClasses.model.Role;
import com.example.backend.utils.utilClasses.IsEntityExists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private IsEntityExists isEntityExists;
    @Autowired
    private UserService userService;

    @Test
    void test() {
        System.out.println(isEntityExists.isUserExists(1));
    }

    @Test
    void testAuthentication1() {
        userService.createUser(
                UserRequestBody.builder()
                        .loginID("003")
                        .password("456")
                        .roles(List.of(Role.ROLE_ADMIN, Role.ROLE_INSTRUCTOR))
                        .roleSpecificInfo(Map.ofEntries(
                                Map.entry("name", "super"),
                                Map.entry("title", "some title")
                        ))
                        .build()
        );
    }

    @Test
    void testAuthentication2() {
        userService.createTechnician(
                UserRequestBody.builder()
                        .loginID("002")
                        .password("852")
                        .roles(List.of(Role.ROLE_TECHNICIAN))
                        .roleSpecificInfo(Map.ofEntries(
                                Map.entry("name", "tech"),
                                Map.entry("title", "some title")
                        ))
                        .build()
        );
    }
}