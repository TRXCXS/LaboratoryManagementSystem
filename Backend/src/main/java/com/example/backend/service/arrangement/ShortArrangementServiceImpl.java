package com.example.backend.service.arrangement;

import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.exception.model.semesterException.SemesterNotExistedException;
import com.example.backend.exception.otherException.NumberIllegalException;
import com.example.backend.mapper.arrangement.ShortArrangementMapper;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShortArrangementServiceImpl implements ShortArrangementService {
    private final ShortArrangementMapper shortArrangementMapper;
    private final IsEntityExists isEntityExists;
    private final ExceptionUtil exceptionUtil;

    @Override
    public void createShortArrangement(@NotNull ShortArrangement newShortArrangement) {
        if (newShortArrangement.getWeek() <= 0) {
            throw new NumberIllegalException("week<=0！");
        }
        exceptionUtil.TypeException(newShortArrangement.getWeekday(), newShortArrangement.getSlot());
        exceptionUtil.LabIDException(newShortArrangement.getLabID());
        if (!isEntityExists.isStudentRequestExists(newShortArrangement.getStudentRequestID())) {
            throw new SemesterNotExistedException("申请不存在，无法创建安排！");
        }

        shortArrangementMapper.insert(newShortArrangement);
    }
}
