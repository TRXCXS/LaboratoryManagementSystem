package com.example.backend.testRepository.user.UserRole;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.entity.user.UserRole;
import com.example.backend.mapper.user.UserRoleMapper;
import com.example.backend.utils.enumClasses.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Test
    void testInsert() {
        UserRole userRole1 = new UserRole(null, 20202531, Role.ROLE_STUDENT);
        UserRole userRole2 = new UserRole(null, 20202532, Role.ROLE_ADMIN);
        UserRole userRole3 = new UserRole(null, 20202533, Role.ROLE_INSTRUCTOR);
        UserRole userRole4 = new UserRole(null, 20202532, Role.ROLE_TECHNICIAN);
        userRoleMapper.insert(userRole1);
        userRoleMapper.insert(userRole2);
        userRoleMapper.insert(userRole3);
        userRoleMapper.insert(userRole4);
    }

    @Test
    void testUpdate() {
        // userRoleMapper自带的update是根据主键进行的，这里显然要根据userID进行更新，所以使用UpdateWrapper

        UpdateWrapper<UserRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userID", 20202533);
        updateWrapper.set("role", Role.ROLE_ADMIN);
        userRoleMapper.update(null, updateWrapper);
    }

    @Test
    void testQuery() {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID", 20202532);
        System.out.println(userRoleMapper.selectList(queryWrapper));

        // 这是无条件查找全部
        System.out.println(userRoleMapper.selectList(null));
    }

    @Test
    void testDelete() {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID", 20202532);
        queryWrapper.eq("role",Role.ROLE_TECHNICIAN);
        userRoleMapper.delete(queryWrapper);
    }
}