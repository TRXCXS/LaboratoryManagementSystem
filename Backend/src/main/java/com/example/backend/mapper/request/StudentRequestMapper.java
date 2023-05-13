package com.example.backend.mapper.request;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend.entity.request.StudentRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentRequestMapper extends BaseMapper<StudentRequest> {
}
