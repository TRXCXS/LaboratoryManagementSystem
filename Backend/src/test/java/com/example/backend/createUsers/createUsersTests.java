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
        // create 3 admins
        for(int i = 1; i <= 3; i++) {
            userService.createUser(
                    UserRequestBody.builder()
                            .loginID(String.format("%03d", i))
                            .password("123456")
                            .roles(List.of(Role.ROLE_ADMIN))
                            .roleSpecificInfo(Map.ofEntries(
                                    Map.entry("name", "admin")
                            ))
                            .build()
            );
        }
    }

    @Test
    void testCreateInstructors() {
        // create 20 admins
        for(int i = 20; i <= 39; i++) {
            userService.createUser(
                    UserRequestBody.builder()
                            .loginID(String.format("%03d", i))
                            .password("123456")
                            .roles(List.of(Role.ROLE_INSTRUCTOR))
                            .roleSpecificInfo(Map.ofEntries(
                                    Map.entry("name", "ins"),
                                    Map.entry("title", "教授")
                            ))
                            .build()
            );
        }
    }

    @Test
    void testCreateTechnicians() {
        // create 10 admins
        for(int i = 60; i <= 69; i++) {
            userService.createUser(
                    UserRequestBody.builder()
                            .loginID(String.format("%03d", i))
                            .password("123456")
                            .roles(List.of(Role.ROLE_TECHNICIAN))
                            .roleSpecificInfo(Map.ofEntries(
                                    Map.entry("name", "tech"),
                                    Map.entry("title", "设备和安全负责人")
                            ))
                            .build()
            );
        }
    }

    @Test
    void testCreateStudents() {
        // create 10 admins
        for(int i = 100; i <= 119; i++) {
            userService.createUser(
                    UserRequestBody.builder()
                            .loginID(String.format("%03d", i))
                            .password("123456")
                            .roles(List.of(Role.ROLE_STUDENT))
                            .roleSpecificInfo(Map.ofEntries(
                                    Map.entry("name", "stu"),
                                    Map.entry("clazz", "20计算机3"),
                                    Map.entry("major", "计算机科学与技术")
                            ))
                            .build()
            );
        }
    }

    @Test
    void testCreateMultiRoleUsers() {
        userService.createUser(
                UserRequestBody.builder()
                        .loginID("150")
                        .password("123456")
                        .roles(List.of(Role.ROLE_INSTRUCTOR,
                                Role.ROLE_TECHNICIAN))
                        .roleSpecificInfo(Map.ofEntries(
                                Map.entry("name", "ti"),
                                Map.entry("instructorTitle", "教授"),
                                Map.entry("technicianTitle", "设备和安全负责人")
                        ))
                        .build()
        );
    }

}
