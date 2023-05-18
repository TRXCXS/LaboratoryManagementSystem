package com.example.backend.service.arrangement;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.responsebody.LongArrangementResponseDataForDisplay;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.mapper.arrangement.LongArrangementMapper;
import com.example.backend.mapper.request.InstructorRequestMapper;
import com.example.backend.utils.enumClasses.requestStatus.InstructorRequestStatus;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LongArrangementServiceImpl implements LongArrangementService {
    private final LongArrangementMapper longArrangementMapper;
    private final InstructorRequestMapper instructorRequestMapper;
    private final IsEntityExists isEntityExists;
    private final ExceptionUtil exceptionUtil;

    @Override
    public void createLongArrangement(@NotNull LongArrangement newLongArrangement) {
        exceptionUtil.WeekException(newLongArrangement.getStartWeek(), newLongArrangement.getEndWeek());
        exceptionUtil.TypeException(newLongArrangement.getWeekday(), newLongArrangement.getSlot());
        exceptionUtil.StudentCountException(newLongArrangement.getStudentCount());
        if (isEntityExists.isInstructorRequestExists(newLongArrangement.getInstructorRequestID())) {
            throw new InstructorNotExistException("申请不存在，无法创建安排！");
        }
        exceptionUtil.LabIDException(newLongArrangement.getLabID());

        longArrangementMapper.insert(newLongArrangement);

        // 更新对应request为arranged
        UpdateWrapper<InstructorRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("instructorRequestID", newLongArrangement.getInstructorRequestID());
        updateWrapper.set("status", InstructorRequestStatus.ARRANGED);
        instructorRequestMapper.update(null, updateWrapper);
    }

    @Override
    public List<LongArrangementResponseDataForDisplay> getTableData() {
        return null;
    }
}
