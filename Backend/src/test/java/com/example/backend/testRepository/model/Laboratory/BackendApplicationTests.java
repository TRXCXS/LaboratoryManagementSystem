package com.example.backend.testRepository.model.Laboratory;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.mapper.model.LaboratoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private LaboratoryMapper laboratoryMapper;

    @Test
    public void testGetLabType() {
        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct labType");
        List<Laboratory> laboratoryList = laboratoryMapper.selectList(queryWrapper);
        System.out.println(laboratoryList);
    }
}