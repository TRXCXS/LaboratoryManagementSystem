package com.example.backend.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.controller.requestbody.UserRequestBodyForUpdate;
import com.example.backend.controller.responsebody.UserData;
import com.example.backend.entity.user.*;
import com.example.backend.exception.enumException.RoleTypeNotExistException;
import com.example.backend.exception.user.administratorException.AdministratorNotExistException;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.exception.user.studentException.StudentNotExistException;
import com.example.backend.exception.user.technicianException.TechnicianNotExistException;
import com.example.backend.exception.user.userException.*;
import com.example.backend.mapper.user.*;
import com.example.backend.utils.enumClasses.model.Role;
import com.example.backend.utils.utilClasses.BatchImportUsers;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRoleMapper userRoleMapper;
    private final UserMapper userMapper;
    private final TechnicianMapper technicianMapper;
    private final InstructorMapper instructorMapper;
    private final StudentMapper studentMapper;
    private final IsEntityExists isEntityExists;
    private final BatchImportUsers batchImportUsers;
    private final AdministratorMapper administratorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createTechnician(@NotNull UserRequestBody technicianInfo) {
        if (isEntityExists.isUserExistsByLoginID(technicianInfo.getLoginID())) {
            throw new UserHasExistedException("LoginID已存在，无法创建！");
        }

        User user = new User(null, technicianInfo.getLoginID(), passwordEncoder.encode(technicianInfo.getPassword()), null);
        userMapper.insert(user);
        Technician technician = new Technician(user.getUserID(), technicianInfo.getRoleSpecificInfo().get("name"), technicianInfo.getRoleSpecificInfo().get("title"));
        technicianMapper.insert(technician);

        // 这里传的technicianInfo.getRoles()虽然是List，但是它必须只有一个值，即只有一个Role，否则可能会出错
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
        if (isEntityExists.isUserExistsByLoginID(instructorInfo.getLoginID())) {
            throw new UserHasExistedException("LoginID已存在，无法创建！");
        }

        User user = new User(null, instructorInfo.getLoginID(), passwordEncoder.encode(instructorInfo.getPassword()), null);
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
        if (isEntityExists.isUserExistsByLoginID(studentInfo.getLoginID())) {
            throw new UserHasExistedException("LoginID已存在，无法创建！");
        }

        User user = new User(null, studentInfo.getLoginID(), passwordEncoder.encode(studentInfo.getPassword()), null);
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
                queryWrapper_technician.eq("technicianID", userID);
                technicianMapper.delete(queryWrapper_technician);
            } else if (userRole.getRole() == Role.ROLE_INSTRUCTOR) {
                QueryWrapper<Instructor> queryWrapper_instructor = new QueryWrapper<>();
                queryWrapper_instructor.eq("instructorID", userID);
                instructorMapper.delete(queryWrapper_instructor);
            } else if (userRole.getRole() == Role.ROLE_STUDENT) {
                QueryWrapper<Student> queryWrapper_student = new QueryWrapper<>();
                queryWrapper_student.eq("studentID", userID);
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
        updateWrapper.set("password", passwordEncoder.encode(resetPasswordRequestInfo.getNewPassword()));
        userMapper.update(null, updateWrapper);
    }

    @Override
    public void checkRolesAndInfo(@NotNull UserRequestBody userInfo) {
        List<Role> roles = userInfo.getRoles();
        Map<String, String> roleSpecificInfo = userInfo.getRoleSpecificInfo();

        if (roles.size() == 0) {
            throw new NoUserRoleException("没有角色，无法创建！");
        }

        if (roles.contains(Role.ROLE_STUDENT) && roles.size() != 1) {
            throw new UserRoleConflictException("角色冲突，请检查！");
        }

        if (!roleSpecificInfo.containsKey("name")) {
            throw new NoNameException("没有name，无法创建！");
        }

        if (roles.contains(Role.ROLE_TECHNICIAN) && !roleSpecificInfo.containsKey("technicianTitle")) {
                throw new NoTitleException("缺少technicianTitle，无法创建！");
        }

        if (roles.contains(Role.ROLE_TECHNICIAN) && !roleSpecificInfo.containsKey("instructorTitle")) {
                throw new NoTitleException("缺少title，无法创建！");
        }

        if (roles.contains(Role.ROLE_STUDENT)) {
            if (!roleSpecificInfo.containsKey("major")) {
                throw new NoMajorException("缺少major，无法创建！");
            }
            if (!roleSpecificInfo.containsKey("clazz")) {
                throw new NoClazzException("缺少clazz，无法创建！");
            }
        }
    }

    @Override
    public void createUser(@NotNull UserRequestBody userInfo) {
        if (isEntityExists.isUserExistsByLoginID(userInfo.getLoginID())){
            throw new UserHasExistedException("LoginID已存在，无法创建！");
        }

        checkRolesAndInfo(userInfo);

        // loginID已存在，无法创建
        if (isEntityExists.isUserExistsByLoginID(userInfo.getLoginID())) {
            throw new UserHasExistedException("loginID已存在，无法更新！");
        }

        List<Role> roles = userInfo.getRoles();
        Map<String, String> roleSpecificInfo = userInfo.getRoleSpecificInfo();

        User user = new User(null, userInfo.getLoginID(), passwordEncoder.encode(userInfo.getPassword()), null);
        userMapper.insert(user);

        if (roles.contains(Role.ROLE_ADMIN)) {
            Administrator administrator = new Administrator(user.getUserID(), roleSpecificInfo.get("name"));
            administratorMapper.insert(administrator);
            UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_ADMIN);
            userRoleMapper.insert(userRole);
        }
        if (roles.contains(Role.ROLE_TECHNICIAN)) {
            Technician technician = new Technician(user.getUserID(), roleSpecificInfo.get("name"), roleSpecificInfo.get("technicianTitle"));
            technicianMapper.insert(technician);
            UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_TECHNICIAN);
            userRoleMapper.insert(userRole);
        }
        if (roles.contains(Role.ROLE_INSTRUCTOR)) {
            Instructor instructor = new Instructor(user.getUserID(), roleSpecificInfo.get("name"), roleSpecificInfo.get("instructorTitle"));
            instructorMapper.insert(instructor);
            UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_INSTRUCTOR);
            userRoleMapper.insert(userRole);
        }
        if (roles.contains(Role.ROLE_STUDENT)) {
            Student student = new Student(user.getUserID(), roleSpecificInfo.get("name"), roleSpecificInfo.get("major"), roleSpecificInfo.get("clazz"));
            studentMapper.insert(student);
            UserRole userRole = new UserRole(null, user.getUserID(), Role.ROLE_STUDENT);
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updateTechnician(@NotNull UserRequestBodyForUpdate userUpdate) {
        if (!isEntityExists.isUserExists(Integer.valueOf(userUpdate.getUserID()))) {
            throw new UserNotExistException("要更新信息的用户不存在！");
        }

        User user = userMapper.selectById(userUpdate.getUserID());
        if (!user.getLoginID().equals(userUpdate.getLoginID())) {
            // loginID不相同，需要更新

            // loginID已存在，无法更新
            if (isEntityExists.isUserExistsByLoginID(userUpdate.getLoginID())) {
                throw new UserHasExistedException("loginID已存在，无法更新！");
            }

            // 更新loginID
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("userID", userUpdate.getUserID());
            updateWrapper.set("loginID", userUpdate.getLoginID());
            userMapper.update(null, updateWrapper);
        }

        // 更新其他信息
        List<Role> roles = userUpdate.getRoles();
        Map<String, String> roleSpecificInfo = userUpdate.getRoleSpecificInfo();

        // 调用这个方法时，默认是一定有Role.ROLE_TECHNICIAN这个角色Role
        Technician technician = technicianMapper.selectById(user.getUserID());

        if ((roleSpecificInfo.containsKey("name")) && (!roleSpecificInfo.get("name").equals(technician.getName()))) {
            // 存在name且新的name不等于旧的name，则更新name
            UpdateWrapper<Technician> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("technicianID", user.getUserID());
            updateWrapper.set("name", roleSpecificInfo.get("name"));
            technicianMapper.update(null, updateWrapper);

            if (roles.contains(Role.ROLE_ADMIN)) {
                // 这个实验员同时还是管理员，要更新管理员表中对应的名字
                UpdateWrapper<Administrator> updateWrapper_administrator = new UpdateWrapper<>();
                updateWrapper_administrator.eq("adminID", user.getUserID());
                updateWrapper_administrator.set("name", roleSpecificInfo.get("name"));
                administratorMapper.update(null, updateWrapper_administrator);
            }

            if (roles.contains(Role.ROLE_INSTRUCTOR)) {
                // 这个实验员同时还是老师，要更新老师表中对应的名字
                UpdateWrapper<Instructor> updateWrapper_instructor = new UpdateWrapper<>();
                updateWrapper_instructor.eq("instructorID", user.getUserID());
                updateWrapper_instructor.set("name", roleSpecificInfo.get("name"));
                instructorMapper.update(null, updateWrapper_instructor);
            }
        }

        if ((roleSpecificInfo.containsKey("technicianTitle")) && (!roleSpecificInfo.get("technicianTitle").equals(technician.getTitle()))) {
            // 更新title
            UpdateWrapper<Technician> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("technicianID", user.getUserID());
            updateWrapper.set("title", roleSpecificInfo.get("technicianTitle"));
            technicianMapper.update(null, updateWrapper);
        }
    }

    @Override
    public void updateInstructor(@NotNull UserRequestBodyForUpdate userUpdate) {
        if (!isEntityExists.isUserExists(Integer.valueOf(userUpdate.getUserID()))) {
            throw new UserNotExistException("要更新信息的用户不存在！");
        }

        User user = userMapper.selectById(userUpdate.getUserID());
        if (!user.getLoginID().equals(userUpdate.getLoginID())) {
            // loginID不相同，需要更新

            // loginID已存在，无法更新
            if (isEntityExists.isUserExistsByLoginID(userUpdate.getLoginID())) {
                throw new UserHasExistedException("loginID已存在，无法更新！");
            }

            // 更新loginID
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("userID", userUpdate.getUserID());
            updateWrapper.set("loginID", userUpdate.getLoginID());
            userMapper.update(null, updateWrapper);
        }

        // 更新其他信息
        List<Role> roles = userUpdate.getRoles();
        Map<String, String> roleSpecificInfo = userUpdate.getRoleSpecificInfo();

        // 调用这个方法时，默认是一定有Role.ROLE_INSTRUCTOR这个角色Role
        Instructor instructor = instructorMapper.selectById(user.getUserID());

        if ((roleSpecificInfo.containsKey("name")) && (!roleSpecificInfo.get("name").equals(instructor.getName()))) {
            // 存在name且新的name不等于旧的name，则更新name
            UpdateWrapper<Instructor> updateWrapper_instructor = new UpdateWrapper<>();
            updateWrapper_instructor.eq("instructorID", user.getUserID());
            updateWrapper_instructor.set("name", roleSpecificInfo.get("name"));
            instructorMapper.update(null, updateWrapper_instructor);

            if (roles.contains(Role.ROLE_ADMIN)) {
                // 这个老师同时还是管理员，要更新管理员表中对应的名字
                UpdateWrapper<Administrator> updateWrapper_administrator = new UpdateWrapper<>();
                updateWrapper_administrator.eq("adminID", user.getUserID());
                updateWrapper_administrator.set("name", roleSpecificInfo.get("name"));
                administratorMapper.update(null, updateWrapper_administrator);
            }

            if (roles.contains(Role.ROLE_TECHNICIAN)) {
                // 这个老师同时还是实验员，要更新实验员表中对应的名字
                UpdateWrapper<Technician> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("technicianID", user.getUserID());
                updateWrapper.set("name", roleSpecificInfo.get("name"));
                technicianMapper.update(null, updateWrapper);
            }
        }

        if ((roleSpecificInfo.containsKey("instructorTitle")) && (!roleSpecificInfo.get("instructorTitle").equals(instructor.getTitle()))) {
            // 更新title
            UpdateWrapper<Instructor> updateWrapper_instructor = new UpdateWrapper<>();
            updateWrapper_instructor.eq("instructorID", user.getUserID());
            updateWrapper_instructor.set("title", roleSpecificInfo.get("instructorTitle"));
            instructorMapper.update(null, updateWrapper_instructor);
        }
    }

    @Override
    public List<Technician> getAllTechnicians() {
        return technicianMapper.selectList(null);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorMapper.selectList(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentMapper.selectList(null);
    }

    @Override
    public List<Technician> getTechniciansByName(String name) {
        QueryWrapper<Technician> queryWrapper = new QueryWrapper<>();
        // 如果传的参数name是空字符串，会查询所有
        // Backend/src/test/java/com/example/backend/testRepository/user/User/BackendApplicationTests.java中做了测试，看起来没有问题
        // 模糊查询是：like %name%
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        List<Technician> technicianList = technicianMapper.selectList(queryWrapper);
        return technicianList;
    }

    @Override
    public List<Instructor> getInstructorsByName(String name) {
        QueryWrapper<Instructor> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        List<Instructor> instructorList = instructorMapper.selectList(queryWrapper);
        return instructorList;
    }

    @Override
    public List<Student> getStudentsByName(String name) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        List<Student> studentList = studentMapper.selectList(queryWrapper);
        return studentList;
    }

    @Override
    public void batchImport(File table, boolean isXls, String userType) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(table);
        batchImportUsers.readAndImport(inputStream, isXls, userType);
    }

    @Override
    public Role[] getRolesByID(Integer userID) {
        User u = userMapper.selectById(userID);
        if(u == null) throw new UserNotExistException("用户不存在");
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("userID", userID));
        Role[] roles = new Role[userRoleList.size()];
        for(int i = 0; i < userRoleList.size(); i++) roles[i] = userRoleList.get(i).getRole();
        return roles;
    }

    @Override
    public Map<String, String> getRoleSpecificInfo(List<Role> roles, Integer userID) {
        Map<String, String> ret = new HashMap<>();
        if(roles.contains(Role.ROLE_ADMIN)) {
            Administrator a = administratorMapper.selectById(userID);
            if(a == null) throw new AdministratorNotExistException("找不到管理员用户信息");
            ret.put("name", a.getName());
        }
        if(roles.contains(Role.ROLE_INSTRUCTOR)) {
            Instructor i = instructorMapper.selectById(userID);
            if(i == null) throw new InstructorNotExistException("找不到教师用户信息");
            ret.put("name", i.getName());
            ret.put("instructorTitle", i.getTitle());
        }
        if(roles.contains(Role.ROLE_TECHNICIAN)) {
            Technician t = technicianMapper.selectById(userID);
            if(t == null) throw new TechnicianNotExistException("找不到实验员用户信息");
            ret.put("name", t.getName());
            ret.put("technicianTitle", t.getTitle());
        }
        if(roles.contains(Role.ROLE_STUDENT)) {
            Student s = studentMapper.selectById(userID);
            if(s == null) throw new StudentNotExistException("找不到学生用户信息");
            ret.put("name", s.getName());
            ret.put("class", s.getClazz());
            ret.put("major", s.getMajor());
        }
        return ret;
    }

    @Override
    public UserData getUserByID(Integer userID) {
        User u = userMapper.selectById(userID);
        if(u == null) throw new UserNotExistException("用户不存在");
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("userID", userID));
        List<Role> roleList = new ArrayList<>();
        for(UserRole ur: userRoleList) roleList.add(ur.getRole());
        Role[] roles = new Role[roleList.size()];
        for(int i = 0; i < roleList.size(); i++) roles[i] = roleList.get(i);
        u.setRole(roles);
        Map<String, String> roleSpecificInfo = getRoleSpecificInfo(roleList, u.getUserID());
        return UserData.builder()
                .userID(u.getUserID())
                .loginID(u.getLoginID())
                .role(u.getRole())
                .roleSpecificInfo(roleSpecificInfo)
                .build();
    }

    @Override
    public void updateStudent(UserRequestBodyForUpdate userUpdate) {
        if (!isEntityExists.isUserExists(Integer.valueOf(userUpdate.getUserID()))) {
            throw new UserNotExistException("要更新信息的用户不存在！");
        }

        User user = userMapper.selectById(userUpdate.getUserID());
        if (!user.getLoginID().equals(userUpdate.getLoginID())) {
            // loginID不相同，需要更新

            // loginID已存在，无法更新
            if (isEntityExists.isUserExistsByLoginID(userUpdate.getLoginID())) {
                throw new UserHasExistedException("loginID已存在，无法更新！");
            }

            // 更新loginID
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("userID", userUpdate.getUserID());
            updateWrapper.set("loginID", userUpdate.getLoginID());
            userMapper.update(null, updateWrapper);
        }

        // 更新其他信息
        List<Role> roles = userUpdate.getRoles();
        Map<String, String> roleSpecificInfo = userUpdate.getRoleSpecificInfo();

        // 调用这个方法时，默认是一定有Role.ROLE_TECHNICIAN这个角色Role
        Student student = studentMapper.selectById(user.getUserID());

        if ((roleSpecificInfo.containsKey("name")) && (!roleSpecificInfo.get("name").equals(student.getName()))) {
            // 存在name且新的name不等于旧的name，则更新name
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("studentID", user.getUserID());
            updateWrapper.set("name", roleSpecificInfo.get("name"));
            studentMapper.update(null, updateWrapper);
        }

        if ((roleSpecificInfo.containsKey("clazz")) && (!roleSpecificInfo.get("clazz").equals(student.getClazz()))) {
            // 更新class
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("studentID", user.getUserID());
            updateWrapper.set("clazz", roleSpecificInfo.get("clazz"));
            studentMapper.update(null, updateWrapper);
        }

        if ((roleSpecificInfo.containsKey("major")) && (!roleSpecificInfo.get("major").equals(student.getMajor()))) {
            // 更新class
            UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("studentID", user.getUserID());
            updateWrapper.set("major", roleSpecificInfo.get("major"));
            studentMapper.update(null, updateWrapper);
        }
    }
}
