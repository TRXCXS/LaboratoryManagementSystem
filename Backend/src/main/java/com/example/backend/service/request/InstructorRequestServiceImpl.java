package com.example.backend.service.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.InstructorRequestRequestBody;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.exception.enumException.LabTypeNotExistException;
import com.example.backend.exception.enumException.SlotTypeNotExistException;
import com.example.backend.exception.enumException.WeekdayTypeNotExistException;
import com.example.backend.exception.model.laboratoryException.StudentCountIllegalException;
import com.example.backend.exception.request.instructorRequestException.InstructorRequestNotExistException;
import com.example.backend.mapper.request.InstructorRequestMapper;
import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.enumClasses.requestStatus.InstructorRequestStatus;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class InstructorRequestServiceImpl implements InstructorRequestService {
    private final InstructorRequestMapper instructorRequestMapper;

    @Override
    public List<InstructorRequest> getAllInstructorRequests() {
        return instructorRequestMapper.selectList(null);
    }

    @Override
    public List<InstructorRequest> getUnhandledInstructorRequests() {
        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", InstructorRequestStatus.NOT_ARRANGED);
        return instructorRequestMapper.selectList(queryWrapper);
    }

    @Override
    public List<InstructorRequest> getInstructorRequestsByInstructor(Integer instructorID) {
        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorID", instructorID);
        return instructorRequestMapper.selectList(queryWrapper);
    }

    @Override
    public void createInstructorRequest(InstructorRequestRequestBody newInstructorRequestInfo) {
        if (!EnumUtils.isValidEnum(LabType.class, newInstructorRequestInfo.getLabType().toString())) {
            // 枚举类型不存在，抛出异常
            throw new LabTypeNotExistException("实验室类型不存在！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, newInstructorRequestInfo.getWeekday().toString())) {
            // 枚举类型不存在，抛出异常
            throw new WeekdayTypeNotExistException("Weekday类型不存在！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, newInstructorRequestInfo.getSlot().toString())) {
            // 枚举类型不存在，抛出异常
            throw new SlotTypeNotExistException("Slot类型不存在！");
        }

        InstructorRequest instructorRequest = new InstructorRequest(
                null,
                new Timestamp(System.currentTimeMillis()),
                newInstructorRequestInfo.getLabType(),

                newInstructorRequestInfo.getStartWeek(),
                newInstructorRequestInfo.getEndWeek(),
                newInstructorRequestInfo.getWeekday(),
                newInstructorRequestInfo.getSlot(),

                newInstructorRequestInfo.getStudentClass(),
                newInstructorRequestInfo.getStudentCount(),
                newInstructorRequestInfo.getCourse(),

                InstructorRequestStatus.NOT_ARRANGED,
                null,
                null,

                newInstructorRequestInfo.getInstructorID(),
                newInstructorRequestInfo.getSemesterID()
        );
        instructorRequestMapper.insert(instructorRequest);
    }

    @Override
    public void updateInstructorRequest(@NotNull InstructorRequestRequestBody updatedInstructorRequestInfo) {
        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorRequestID", updatedInstructorRequestInfo.getInstructorRequestID());
        if (!instructorRequestMapper.exists(queryWrapper)) {
            throw new InstructorRequestNotExistException("目标申请不存在，无法更新！");
        }

        if (!EnumUtils.isValidEnum(LabType.class, updatedInstructorRequestInfo.getLabType().toString())) {
            // 枚举类型不存在，抛出异常
            throw new LabTypeNotExistException("实验室类型不存在！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, updatedInstructorRequestInfo.getWeekday().toString())) {
            // 枚举类型不存在，抛出异常
            throw new WeekdayTypeNotExistException("Weekday类型不存在！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, updatedInstructorRequestInfo.getSlot().toString())) {
            // 枚举类型不存在，抛出异常
            throw new SlotTypeNotExistException("Slot类型不存在！");
        }

        if (updatedInstructorRequestInfo.getStudentCount() <= 0) {
            throw new StudentCountIllegalException("学生人数不合法（<=0）！");
        }

        UpdateWrapper<InstructorRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("instructorRequestID", updatedInstructorRequestInfo.getInstructorRequestID());

        updateWrapper.set("labType", updatedInstructorRequestInfo.getLabType());
        updateWrapper.set("startWeek", updatedInstructorRequestInfo.getStartWeek());
        updateWrapper.set("endWeek", updatedInstructorRequestInfo.getEndWeek());
        updateWrapper.set("weekday", updatedInstructorRequestInfo.getWeekday());
        updateWrapper.set("slot", updatedInstructorRequestInfo.getSlot());
        updateWrapper.set("studentClass", updatedInstructorRequestInfo.getStudentClass());
        updateWrapper.set("studentCount", updatedInstructorRequestInfo.getStudentCount());
        updateWrapper.set("course", updatedInstructorRequestInfo.getCourse());
        updateWrapper.set("instructorID", updatedInstructorRequestInfo.getInstructorID());
        updateWrapper.set("semesterID", updatedInstructorRequestInfo.getSemesterID());

        instructorRequestMapper.update(null, updateWrapper);
    }
}
