package com.example.backend.controller.user;

import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.exception.user.userRequestException.MultipleRoleException;
import com.example.backend.exception.user.userRequestException.RoleSpecificInfoNotFoundException;
import com.example.backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private final UserService userService;

    /**
     * 创建实验员
     * 该接口只接受单角色实验员用户
     * 要求在角色特定信息roleSpecificInfo里填写name和title
     */
    @PostMapping("/technician")
    public GeneralFormattedResponseBody<Object>
            createTechnician(
                    @RequestBody UserRequestBody technicianInfo
    ) {
        if(technicianInfo.getRoles().size() != 1) {
            throw new MultipleRoleException("该接口只接受单角色用户创建");
        }
        Map<String, String> roleSpecificInfo = technicianInfo.getRoleSpecificInfo();
        if(!roleSpecificInfo.containsKey("name")
                || !roleSpecificInfo.containsKey("title")) {
            throw new RoleSpecificInfoNotFoundException(
                    "创建角色实验员要求填写姓名和职称"
            );
        }
        userService.createTechnician(technicianInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * 创建教师
     * 单角色用户创建
     * 要求在角色特定信息roleSpecificInfo里填写name和title
     */
    @PostMapping("/instructor")
    public GeneralFormattedResponseBody<Object>
    createInstructor(
            @RequestBody UserRequestBody instructorInfo
    ) {
        if(instructorInfo.getRoles().size() != 1) {
            throw new MultipleRoleException("该接口只接受单角色用户创建");
        }
        Map<String, String> roleSpecificInfo = instructorInfo.getRoleSpecificInfo();
        if(!roleSpecificInfo.containsKey("name")
                || !roleSpecificInfo.containsKey("title")) {
            throw new RoleSpecificInfoNotFoundException(
                    "创建角色教师要求填写姓名和职称"
            );
        }
        userService.createInstructor(instructorInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * 创建学生
     * 单角色用户创建
     * 要求在角色特定信息（roleSpecificInfo）里填写
     * name和major和clazz
     */
    @PostMapping("/student")
    public GeneralFormattedResponseBody<Object>
    createStudent(
            @RequestBody UserRequestBody studentInfo
    ) {
        if(studentInfo.getRoles().size() != 1) {
            throw new MultipleRoleException("该接口只接受单角色用户创建");
        }
        Map<String, String> roleSpecificInfo = studentInfo.getRoleSpecificInfo();
        if(!roleSpecificInfo.containsKey("name")
                || !roleSpecificInfo.containsKey("major")
                || !roleSpecificInfo.containsKey("clazz")
        ) {
            throw new RoleSpecificInfoNotFoundException(
                    "创建角色学生要求填写姓名、专业和班级"
            );
        }
        userService.createStudent(studentInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }


    @DeleteMapping
    public GeneralFormattedResponseBody<Object>
    deleteUser(@RequestParam Integer userID) {
        userService.deleteUser(userID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/password")
    public GeneralFormattedResponseBody<Object>
    resetPassword(@RequestBody ResetPasswordRequestBody resetPasswordRequestInfo) {
        userService.resetPassword(resetPasswordRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

//    @PostMapping
//    public GeneralFormattedResponseBody<Object>
//    createUser(
//            @RequestBody UserRequestBody userInfo
//    ) {
//        userService.checkRolesAndInfo(userInfo);
//        userService.createUser(userInfo);
//        return GeneralFormattedResponseBody
//                .<Object>builder()
//                .status(HttpStatus.CREATED.value())
//                .message("success")
//                .data(null)
//                .build();
//    }

}
