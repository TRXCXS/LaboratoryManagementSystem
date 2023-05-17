package com.example.backend.testRepository.user.User;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.entity.user.User;
import com.example.backend.mapper.user.UserMapper;
import com.example.backend.utils.enumClasses.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user1 = new User(null, "202034510311", "123456", new Role[]{Role.ROLE_ADMIN, Role.ROLE_TECHNICIAN});
        User user2 = new User(null, "2021", "123456", new Role[]{Role.ROLE_INSTRUCTOR});
        User user3 = new User(null, "2022", "123456", new Role[]{Role.ROLE_STUDENT});

        userMapper.insert(user1);
        userMapper.insert(user2);
        userMapper.insert(user3);
    }

    @Test
    void testUpdate() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("loginID", 2022);
        updateWrapper.set("password", 123);
        userMapper.update(null, updateWrapper);
    }

    @Test
    void testQuery() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginID", 2021);
        System.out.println(userMapper.selectList(queryWrapper));

        // 这是无条件查找全部
        System.out.println(userMapper.selectList(null));
    }

    @Test
    void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginID", 2022);
        userMapper.delete(queryWrapper);
    }

    @Test
    void testExists() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginID", "2021");
        System.out.println("是否存在loginID为2021的user?:" + userMapper.exists(queryWrapper));

        queryWrapper.eq("loginID", "2022");
        System.out.println("是否存在loginID为2022的user?:" + userMapper.exists(queryWrapper));
    }
}