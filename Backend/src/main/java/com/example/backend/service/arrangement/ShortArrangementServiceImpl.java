package com.example.backend.service.arrangement;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.exception.model.semesterException.SemesterNotExistedException;
import com.example.backend.exception.otherException.NumberIllegalException;
import com.example.backend.exception.request.instructorRequestException.InstructorRequestNotExistException;
import com.example.backend.mapper.arrangement.ShortArrangementMapper;
import com.example.backend.mapper.request.StudentRequestMapper;
import com.example.backend.service.model.CurrentSemesterService;
import com.example.backend.utils.enumClasses.requestStatus.StudentRequestStatus;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShortArrangementServiceImpl implements ShortArrangementService {
    private final ShortArrangementMapper shortArrangementMapper;
    private final StudentRequestMapper studentRequestMapper;private final CurrentSemesterService currentSemesterService;
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

        // 更新对应request为arranged
        UpdateWrapper<StudentRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("studentRequestID", newShortArrangement.getStudentRequestID());
        updateWrapper.set("status", StudentRequestStatus.APPROVED);
        updateWrapper.set("adminProcessTime", new Timestamp(System.currentTimeMillis()));
        studentRequestMapper.update(null, updateWrapper);
    }

    @Override
    public List<ShortArrangement> getAllShortArrangements() {
        List<ShortArrangement> list = new ArrayList<>();

        List<ShortArrangement> shortArrangementList = shortArrangementMapper.selectList(null);

        for (ShortArrangement shortArrangement : shortArrangementList) {
            if (!isEntityExists.isStudentRequestExists(shortArrangement.getStudentRequestID())) {
                throw new InstructorRequestNotExistException("StudentRequest不存在！");
            }
            StudentRequest studentRequest = studentRequestMapper.selectById(shortArrangement.getStudentRequestID());

            // 获取所有当前学期的安排信息
            if (Objects.equals(studentRequest.getSemesterID(), currentSemesterService.getCurrentSemester().getSemesterID())) {
                list.add(shortArrangement);
            }
        }

        return list;
    }
}
