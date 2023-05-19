package com.example.backend.service.arrangement;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.responsebody.LongArrangementResponseDataForDisplay;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.entity.user.Instructor;
import com.example.backend.exception.model.laboratoryException.LaboratoryNotExistedException;
import com.example.backend.exception.request.instructorRequestException.InstructorRequestNotExistException;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.mapper.arrangement.LongArrangementMapper;
import com.example.backend.mapper.model.LaboratoryMapper;
import com.example.backend.mapper.request.InstructorRequestMapper;
import com.example.backend.mapper.user.InstructorMapper;
import com.example.backend.service.model.CurrentSemesterService;
import com.example.backend.utils.enumClasses.requestStatus.InstructorRequestStatus;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LongArrangementServiceImpl implements LongArrangementService {
    private final LongArrangementMapper longArrangementMapper;
    private final InstructorRequestMapper instructorRequestMapper;
    private final InstructorMapper instructorMapper;
    private final LaboratoryMapper laboratoryMapper;
    private final CurrentSemesterService currentSemesterService;
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
        List<LongArrangementResponseDataForDisplay> list = new ArrayList<>();

        List<LongArrangement> longArrangementList = longArrangementMapper.selectList(null);

        for (LongArrangement longArrangement : longArrangementList) {
            if (isEntityExists.isInstructorRequestExists(longArrangement.getInstructorRequestID())) {
                throw new InstructorRequestNotExistException("InstructorRequest不存在！");
            }
            InstructorRequest instructorRequest = instructorRequestMapper.selectById(longArrangement.getInstructorRequestID());

            // 获取所有当前学期的安排信息，用来展示
            if (Objects.equals(instructorRequest.getSemesterID(), currentSemesterService.getCurrentSemester().getSemesterID())) {
                if (isEntityExists.isInstructorExists(instructorRequest.getInstructorID())) {
                    throw new InstructorNotExistException("Instructor不存在！");
                }
                Instructor instructor = instructorMapper.selectById(instructorRequest.getInstructorID());

                if (isEntityExists.isLaboratoryExists(longArrangement.getLabID())) {
                    throw new LaboratoryNotExistedException("Laboratory不存在！");
                }
                Laboratory laboratory = laboratoryMapper.selectById(longArrangement.getLabID());

                LongArrangementResponseDataForDisplay longArrangementResponseDataForDisplay = new LongArrangementResponseDataForDisplay(
                        longArrangement.getLongArrangementID(),

                        longArrangement.getStartWeek(),
                        longArrangement.getEndWeek(),
                        longArrangement.getWeekday(),
                        longArrangement.getSlot(),

                        longArrangement.getStudentClass(),
                        longArrangement.getStudentCount(),

                        instructorRequest.getCourse(),
                        instructor.getName(),

                        longArrangement.getLabID(),
                        Integer.valueOf(laboratory.getLabNumber())
                );

                list.add(longArrangementResponseDataForDisplay);
            }
        }

        return list;
    }

    @Override
    public List<LongArrangement> getAllLongArrangements() {
        List<LongArrangement> list = new ArrayList<>();

        List<LongArrangement> longArrangementList = longArrangementMapper.selectList(null);

        for (LongArrangement longArrangement : longArrangementList) {
            if (isEntityExists.isInstructorRequestExists(longArrangement.getInstructorRequestID())) {
                throw new InstructorRequestNotExistException("InstructorRequest不存在！");
            }
            InstructorRequest instructorRequest = instructorRequestMapper.selectById(longArrangement.getInstructorRequestID());

            // 获取所有当前学期的安排信息
            if (Objects.equals(instructorRequest.getSemesterID(), currentSemesterService.getCurrentSemester().getSemesterID())) {
                list.add(longArrangement);
            }
        }

        return list;
    }
}
