package com.example.backend.service.user;

import com.example.backend.controller.requestbody.ResetPasswordRequestBody;
import com.example.backend.controller.requestbody.UserRequestBody;
import com.example.backend.controller.requestbody.UserRequestBodyForUpdate;
import com.example.backend.entity.user.Instructor;
import com.example.backend.entity.user.Student;
import com.example.backend.entity.user.Technician;
import com.example.backend.utils.enumClasses.model.Role;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

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

    /**
     * 检查用户信息userInfo里的角色和角色特定信息
     * <p>
     * 这个方法用于在创建多角色用户之前，对客户端提供的用户信息进行检查
     * 实现时，可以创建并调用2个服务层实现类impl的私有方法：
     * private void checkRoles()，检查用户角色是否符合要求，比如
     * “不能同时存在老师和学生这2个角色”
     * private void checkRoleSpecificInfo()，检查角色特定信息是否全面，比如
     * “存在管理员角色，那么至少要提供‘姓名’”
     * <p>
     * 这2个私有方法还有调用它的接口方法，都没有返回值
     * 成功返回则通过检查
     * 一旦检查出异常，就立刻抛异常，不会成功执行完毕
     */
    void checkRolesAndInfo(UserRequestBody userInfo);

    /**
     * 创建多角色用户
     * 需要：
     * 0、检查loginID是否唯一
     * 1、向User表插入表项
     * 2、向各个子表插入表项
     * 3、向UserRole表插入表项
     * <p>
     * 第0步需要实现为impl类的一个私有方法，
     * 并在各类创建方法中（不止这一个）事先调用判断
     */
    void createUser(UserRequestBody userInfo);

    /**
     * 更新实验员信息
     * 需要做以下的事情，其他更新方法同理：
     * 1、查询userID对应的用户信息x
     * 2、比较x中的loginID和updateInfo中的loginID是否相同
     * 若相同，则说明没有更新这项信息
     * 若不同，则说明需要改变，调用查重的私有方法，确认loginID无重复，并改变
     * 3、依次对比并改变roleSpecificInfo中实验员的其他信息:
     * 比如对于信息之一name
     * 若没有，则不用更新
     * 若有但是仍然一样，不用更新
     * 若有且不一样，需要更新
     * 4、最好可以做到，当name发生改变时，其他角色子表里的name也发生改变
     * title应该是不用跟着改变的，实验室的职称和教师的职称应该是不同的2个概念
     * <p>
     * 注：
     * 1、此接口不对用户的权限做更改，只修改基本用户信息，roles只用于查询
     * 2、针对的是单一角色的实验员用户，但改到名字时最好同时更新其他子表
     * 3、userID也是不可改变的，只用于查询，若查询不到也算作异常
     */
    void updateTechnician(UserRequestBodyForUpdate userUpdate);

    void updateInstructor(UserRequestBodyForUpdate userUpdate);

    List<Technician> getAllTechnicians();

    List<Instructor> getAllInstructors();

    List<Student> getAllStudents();

    /**
     * 用like进行模糊查询
     */
    List<Technician> getTechniciansByName(String name);

    List<Instructor> getInstructorsByName(String name);

    List<Student> getStudentsByName(String name);

    void batchImport(File table, boolean isXls, String userType) throws FileNotFoundException;

    Map<String, String> getRoleSpecificInfo(List<Role> roles, Integer userID);
}
