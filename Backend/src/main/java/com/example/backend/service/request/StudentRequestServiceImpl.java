package com.example.backend.service.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.StudentRequestRequestBody;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.exception.enumException.SlotTypeNotExistException;
import com.example.backend.exception.enumException.WeekdayTypeNotExistException;
import com.example.backend.exception.model.laboratoryException.LaboratoryHasBeenArrangedException;
import com.example.backend.exception.model.laboratoryException.LaboratoryNotExistedException;
import com.example.backend.exception.request.studentRequestException.StudentRequestNotExistException;
import com.example.backend.mapper.model.LaboratoryMapper;
import com.example.backend.mapper.request.StudentRequestMapper;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.enumClasses.requestStatus.StudentRequestStatus;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentRequestServiceImpl implements StudentRequestService {
    private final StudentRequestMapper studentRequestMapper;
    private final LaboratoryMapper laboratoryMapper;

    @Override
    public List<StudentRequest> getStudentRequestsByStudent(Integer studentID) {
        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentID", studentID);
        return studentRequestMapper.selectList(queryWrapper);
    }

    @Override
    public void createStudentRequest(@NotNull StudentRequestRequestBody newStudentRequestInfo) {
        QueryWrapper<Laboratory> labQueryWrapper = new QueryWrapper<>();
        labQueryWrapper.eq("labID", newStudentRequestInfo.getLabID());
        if (!laboratoryMapper.exists(labQueryWrapper)) {
            throw new LaboratoryNotExistedException("目标实验室不存在，申请失败！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, newStudentRequestInfo.getWeekday().toString())) {
            // 枚举类型不存在，抛出异常
            throw new WeekdayTypeNotExistException("Weekday类型不存在！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, newStudentRequestInfo.getSlot().toString())) {
            // 枚举类型不存在，抛出异常
            throw new SlotTypeNotExistException("Slot类型不存在！");
        }

        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semesterID", newStudentRequestInfo.getSemesterID());
        queryWrapper.eq("week", newStudentRequestInfo.getWeek());
        queryWrapper.eq("weekday", newStudentRequestInfo.getWeekday());
        queryWrapper.eq("slot", newStudentRequestInfo.getSlot());
        queryWrapper.eq("labID", newStudentRequestInfo.getLabID());
        if (studentRequestMapper.exists(queryWrapper)) {
            throw new LaboratoryHasBeenArrangedException("该实验室在该时间段已经被借用，因此无法申请！");
        }

        StudentRequest studentRequest = new StudentRequest(
                null,
                newStudentRequestInfo.getWeek(),
                newStudentRequestInfo.getWeekday(),
                newStudentRequestInfo.getSlot(),
                newStudentRequestInfo.getReason(),
                new Timestamp(System.currentTimeMillis()),
                StudentRequestStatus.NOT_VIEWED,
                null,
                null,
                null,
                newStudentRequestInfo.getLabID(),
                newStudentRequestInfo.getSemesterID(),
                newStudentRequestInfo.getStudentID()
        );
        studentRequestMapper.insert(studentRequest);
    }

    @Override
    public void updateStudentRequest(@NotNull StudentRequestRequestBody updatedStudentRequestInfo) {
        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentRequestID", updatedStudentRequestInfo.getStudentRequestID());
        if (!studentRequestMapper.exists(queryWrapper)) {
            throw new StudentRequestNotExistException("目标申请不存在，无法更新！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, updatedStudentRequestInfo.getWeekday().toString())) {
            // 枚举类型不存在，抛出异常
            throw new WeekdayTypeNotExistException("Weekday类型不存在！");
        }

        if (!EnumUtils.isValidEnum(Weekday.class, updatedStudentRequestInfo.getSlot().toString())) {
            // 枚举类型不存在，抛出异常
            throw new SlotTypeNotExistException("Slot类型不存在！");
        }

        queryWrapper.clear();
        queryWrapper.eq("semesterID", updatedStudentRequestInfo.getSemesterID());
        queryWrapper.eq("week", updatedStudentRequestInfo.getWeek());
        queryWrapper.eq("weekday", updatedStudentRequestInfo.getWeekday());
        queryWrapper.eq("slot", updatedStudentRequestInfo.getSlot());
        queryWrapper.eq("labID", updatedStudentRequestInfo.getLabID());
        if (studentRequestMapper.exists(queryWrapper)) {
            throw new LaboratoryHasBeenArrangedException("该实验室在该时间段已经被借用，因此无法变更申请！");
        }

        UpdateWrapper<StudentRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("studentRequestID", updatedStudentRequestInfo.getStudentRequestID());

        updateWrapper.set("week", updatedStudentRequestInfo.getWeek());
        updateWrapper.set("weekday", updatedStudentRequestInfo.getWeekday());
        updateWrapper.set("slot", updatedStudentRequestInfo.getSlot());
        updateWrapper.set("reason", updatedStudentRequestInfo.getReason());
        updateWrapper.set("adminProcessTime", new Timestamp(System.currentTimeMillis()));
        updateWrapper.set("labID", updatedStudentRequestInfo.getLabID());
        updateWrapper.set("semesterID", updatedStudentRequestInfo.getSemesterID());
        updateWrapper.set("studentID", updatedStudentRequestInfo.getStudentID());

        studentRequestMapper.update(null, updateWrapper);
    }

    @Override
    public void setUseComplete(Integer studentRequestID) {
        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentRequestID", studentRequestID);
        if (!studentRequestMapper.exists(queryWrapper)) {
            throw new StudentRequestNotExistException("目标申请不存在，无法更新！");
        }

        UpdateWrapper<StudentRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("studentRequestID", studentRequestID);

        updateWrapper.set("status", StudentRequestStatus.USE_COMPLETE);
        updateWrapper.set("useCompleteTime", new Timestamp(System.currentTimeMillis()));

        studentRequestMapper.update(null, updateWrapper);
    }
}
