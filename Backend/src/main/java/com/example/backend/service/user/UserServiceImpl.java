package com.example.backend.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.entity.user.*;
import com.example.backend.exception.enumException.RoleTypeNotExistException;
import com.example.backend.exception.user.userException.UserNotExistException;
import com.example.backend.mapper.user.*;
import com.example.backend.utils.enumClasses.model.Role;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRoleMapper userRoleMapper;
    private final UserMapper userMapper;
    private final TechnicianMapper technicianMapper;
    private final InstructorMapper instructorMapper;
    private final StudentMapper studentMapper;
    private final IsEntityExists isEntityExists;

    @Override
    public void createTechnician(@NotNull UserRequestBody technicianInfo) {
        User user = new User(null, technicianInfo.getLoginID(), technicianInfo.getPassword(), null);
        userMapper.insert(user);
        Technician technician = new Technician(user.getUserID(), technicianInfo.getRoleSpecificInfo().get("name"), technicianInfo.getRoleSpecificInfo().get("title"));
        technicianMapper.insert(technician);

        for (Role role : technicianInfo.getRoles()) {
            if (!EnumUtils.isValidEnum(Role.class, role.toString())) {
                // 枚举类型不存在，抛出异常
                throw new RoleTypeNotExistException("Role类型不存在！");
            }

            UserRole userRole = new UserRole(null, user.getUserID(), role);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void createInstructor(@NotNull UserRequestBody instructorInfo) {
        User user = new User(null, instructorInfo.getLoginID(), instructorInfo.getPassword(), null);
        userMapper.insert(user);
        Instructor instructor = new Instructor(user.getUserID(), instructorInfo.getRoleSpecificInfo().get("name"), instructorInfo.getRoleSpecificInfo().get("title"));
        instructorMapper.insert(instructor);

        for (Role role : instructorInfo.getRoles()) {
            if (!EnumUtils.isValidEnum(Role.class, role.toString())) {
                // 枚举类型不存在，抛出异常
                throw new RoleTypeNotExistException("Role类型不存在！");
            }

            UserRole userRole = new UserRole(null, user.getUserID(), role);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void createStudent(@NotNull UserRequestBody studentInfo) {
        User user = new User(null, studentInfo.getLoginID(), studentInfo.getPassword(), null);
        userMapper.insert(user);
        Student student = new Student(user.getUserID(), studentInfo.getRoleSpecificInfo().get("name"), studentInfo.getRoleSpecificInfo().get("major"), studentInfo.getRoleSpecificInfo().get("clazz"));
        studentMapper.insert(student);

        UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_STUDENT);
        userRoleMapper.insert(userRole);
    }

    @Override
    public void deleteUser(Integer userID) {
        if (!isEntityExists.isUserExists(userID)) {
            throw new UserNotExistException("要删除的用户不存在！");
        }

        // 列出用户所有角色
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID", userID);
        List<UserRole> userRoleList = userRoleMapper.selectList(queryWrapper);

        // 在各个角色子表里删除用户
        for (UserRole userRole : userRoleList) {
            if (userRole.getRole() == Role.ROLE_TECHNICIAN) {
                QueryWrapper<Technician> queryWrapper_technician = new QueryWrapper<>();
                queryWrapper_technician.eq("userID", userID);
                technicianMapper.delete(queryWrapper_technician);
            } else if (userRole.getRole() == Role.ROLE_INSTRUCTOR) {
                QueryWrapper<Instructor> queryWrapper_instructor = new QueryWrapper<>();
                queryWrapper_instructor.eq("userID", userID);
                instructorMapper.delete(queryWrapper_instructor);
            } else if (userRole.getRole() == Role.ROLE_STUDENT) {
                QueryWrapper<Student> queryWrapper_student = new QueryWrapper<>();
                queryWrapper_student.eq("userID", userID);
                studentMapper.delete(queryWrapper_student);
            }
        }

        // 删除user_role表里这个用户的项
        userRoleMapper.delete(queryWrapper);

        // 删除user表项
        QueryWrapper<User> queryWrapper_user = new QueryWrapper<>();
        queryWrapper_user.eq("userID", userID);
        userMapper.delete(queryWrapper_user);
    }

    @Override
    public void resetPassword(@NotNull ResetPasswordRequestBody resetPasswordRequestInfo) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userID", resetPasswordRequestInfo.getUserID());
        updateWrapper.set("password", resetPasswordRequestInfo.getNewPassword());
        userMapper.update(null, updateWrapper);
    }
}
