package com.example.backend.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.entity.user.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
