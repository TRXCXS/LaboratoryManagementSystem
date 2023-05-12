package com.example.backend.service.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.exception.enumException.LabTypeNotExistException;
import com.example.backend.exception.model.laboratoryException.StudentCountIllegalException;
import com.example.backend.mapper.model.LaboratoryMapper;
import com.example.backend.utils.enumClasses.model.LabType;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryServiceImpl implements LaboratoryService {
    private final LaboratoryMapper laboratoryMapper;

    @Override
    public List<Laboratory> getLabsByType(String labType) {
        if (!EnumUtils.isValidEnum(LabType.class, labType)) {
            // 枚举类型不存在，抛出异常
            throw new LabTypeNotExistException("实验室类型不存在！");
        }

        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labType", labType);
        return laboratoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Laboratory> getLabsByCapacity(Integer studentCount) {
        if (studentCount <= 0) {
            throw new StudentCountIllegalException("学生人数不合法（<=0）！");
        }

        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("deviceCount", studentCount);
        return laboratoryMapper.selectList(queryWrapper);
    }
}
