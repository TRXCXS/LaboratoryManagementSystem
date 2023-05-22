package com.example.backend.createUsers;

import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.service.user.UserService;
import com.example.backend.utils.enumClasses.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class createUsersTests {
    @Autowired
    private UserService userService;

    @Test
    void testCreateAdmins() {
        for(int i = 1; i <= 3; i++) {
            userService.createUser(
                    UserRequestBody.builder()
                            .loginID("01" + i)
                            .password("123456")
                            .roles(List.of(Role.ROLE_ADMIN))
                            .roleSpecificInfo(Map.ofEntries(
                                    Map.entry("name", "INS"),
                                    Map.entry("title", "教授")
                            ))
                            .build()
            );
        }
    }


}
