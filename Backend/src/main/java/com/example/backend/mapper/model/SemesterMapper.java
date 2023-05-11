package com.example.backend.mapper.model;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.entity.model.Semester;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SemesterMapper extends BaseMapper<Semester> {
}
