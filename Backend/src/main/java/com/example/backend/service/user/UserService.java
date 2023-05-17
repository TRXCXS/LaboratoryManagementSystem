package com.example.backend.service.user;

import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    /**
     * 创建实验员
     * 角色只有“实验员”
     * 在technicianInfo的角色特定信息roleSpecificInfo里
     * 已经有name和title这2个key
     * 需要更新user表，user_role表和technician表
     */
    void createTechnician(UserRequestBody technicianInfo);

    /**
     * 创建教师
     * 与创建实验员的参数条件相同
     */
    void createInstructor(UserRequestBody instructorInfo);

    /**
     * 创建学生
     * 参数里已有name, clazz, major
     */
    void createStudent(UserRequestBody studentInfo);

    /**
     * 删除用户
     * 至少需要做：
     * 1、列出用户所有角色
     * 2、在各个角色子表里删除用户
     * 3、删除user_role表里这个用户的项
     * 4、删除user表项
     */
    void deleteUser(Integer userID);

    void resetPassword(ResetPasswordRequestBody resetPasswordRequestInfo);
}
