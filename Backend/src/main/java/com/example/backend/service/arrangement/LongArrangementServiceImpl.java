package com.example.backend.service.arrangement;

import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.mapper.arrangement.LongArrangementMapper;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LongArrangementServiceImpl implements LongArrangementService {
    private final LongArrangementMapper longArrangementMapper;
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
        // TODO: 2023/5/15 要更新对应request为arranged
    }
}
