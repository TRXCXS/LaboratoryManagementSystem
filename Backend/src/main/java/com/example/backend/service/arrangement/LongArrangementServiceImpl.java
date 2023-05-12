package com.example.backend.service.arrangement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.exception.arrangement.longArrangementException.LongArrangementHasExistedException;
import com.example.backend.exception.model.laboratoryException.LaboratoryDevicesNotEnoughException;
import com.example.backend.exception.model.laboratoryException.LaboratoryHasBeenArrangedException;
import com.example.backend.exception.model.laboratoryException.LaboratoryNotExistedException;
import com.example.backend.exception.model.laboratoryException.StudentCountIllegalException;
import com.example.backend.mapper.arrangement.LongArrangementMapper;
import com.example.backend.mapper.model.LaboratoryMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LongArrangementServiceImpl implements LongArrangementService {
    private final LongArrangementMapper longArrangementMapper;
    private final LaboratoryMapper laboratoryMapper;

    @Override
    public void createLongArrangement(@NotNull LongArrangement newLongArrangement) {
        if (newLongArrangement.getStudentCount() <= 0) {
            throw new StudentCountIllegalException("学生人数不合法（<=0）！");
        }

        QueryWrapper<Laboratory> labQueryWrapper = new QueryWrapper<>();
        labQueryWrapper.eq("labID", newLongArrangement.getLabID());
        if (!laboratoryMapper.exists(labQueryWrapper)) {
            throw new LaboratoryNotExistedException("目标实验室不存在，无法创建安排！");
        }

        QueryWrapper<LongArrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorRequestID", newLongArrangement.getInstructorRequestID());
        if (longArrangementMapper.exists(queryWrapper)) {
            throw new LongArrangementHasExistedException("该申请的安排已经存在，无法再次创建安排！");
        }

        labQueryWrapper.clear();
        labQueryWrapper.eq("labID", newLongArrangement.getLabID());
        if (laboratoryMapper.selectOne(labQueryWrapper).getDeviceCount() < newLongArrangement.getStudentCount()) {
            throw new LaboratoryDevicesNotEnoughException("目标实验室设备不够，无法创建安排！");
        }

        queryWrapper.clear();
        queryWrapper.and(Wrapper -> Wrapper
                .lt("startWeek", newLongArrangement.getEndWeek())
                .or()
                .gt("endWeek", newLongArrangement.getStartWeek())
                .or()
                .ge("startWeek", newLongArrangement.getStartWeek())
                .le("endWeek", newLongArrangement.getEndWeek())
        );
        queryWrapper.eq("weekday", newLongArrangement.getWeekday());
        queryWrapper.eq("slot", newLongArrangement.getSlot());
        queryWrapper.eq("labID", newLongArrangement.getLabID());
        if (longArrangementMapper.exists(queryWrapper)) {
            throw new LaboratoryHasBeenArrangedException("该实验室在该时间段已经被借用，因此无法创建安排！");
        }

        longArrangementMapper.insert(newLongArrangement);
    }
}
