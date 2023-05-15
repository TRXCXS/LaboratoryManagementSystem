package com.example.backend.testRepository.model.Semester;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.entity.model.Semester;
import com.example.backend.mapper.model.SemesterMapper;
import com.example.backend.utils.enumClasses.model.Season;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private SemesterMapper semesterMapper;

    @Test
    void testInsert() {
        Semester semester1 = new Semester(null, 2022, 2023, Season.FALL, new Timestamp(System.currentTimeMillis()), 18);
        Semester semester2 = new Semester(null, 2022, 2023, Season.SPRING, new Timestamp(System.currentTimeMillis()), 20);

        semesterMapper.insert(semester1);
        semesterMapper.insert(semester2);
    }

    @Test
    void testUpdate() {
        UpdateWrapper<Semester> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("season", Season.FALL);
        updateWrapper.set("weekCount", 23);
        semesterMapper.update(null, updateWrapper);
    }

    @Test
    void testQuery() {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("weekCount", 20);
        System.out.println(semesterMapper.selectList(queryWrapper));

        // 这是无条件查找全部
        System.out.println(semesterMapper.selectList(null));
    }

    @Test
    void testDelete() {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("weekCount", 20);
        semesterMapper.delete(queryWrapper);
    }

    @Test
    void testExists() {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("weekCount", 20);
        System.out.println("是否存在weekCount为20的user?:" + semesterMapper.exists(queryWrapper));

        queryWrapper.eq("weekCount", 23);
        System.out.println("是否存在weekCount为23的user?:" + semesterMapper.exists(queryWrapper));
    }
}