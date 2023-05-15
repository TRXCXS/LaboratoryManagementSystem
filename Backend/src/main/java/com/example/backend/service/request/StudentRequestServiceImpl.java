package com.example.backend.service.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.StudentRequestRequestBody;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.exception.model.semesterException.SemesterNotExistedException;
import com.example.backend.exception.otherException.NumberIllegalException;
import com.example.backend.exception.request.studentRequestException.StudentRequestHasExistedException;
import com.example.backend.exception.request.studentRequestException.StudentRequestNotExistException;
import com.example.backend.exception.user.studentException.StudentNotExistException;
import com.example.backend.mapper.request.StudentRequestMapper;
import com.example.backend.utils.enumClasses.requestStatus.StudentRequestStatus;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentRequestServiceImpl implements StudentRequestService {
    private final StudentRequestMapper studentRequestMapper;
    private final IsEntityExists isEntityExists;
    private final ExceptionUtil exceptionUtil;

    @Override
    public List<StudentRequest> getStudentRequestsByStudent(Integer studentID) {
        // TODO: 2023/5/14 这里的 semesterID应该是根据CurrentSemesterService拿到的当前学期的ID
        int semesterID = 0;

        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentID", studentID);
        queryWrapper.eq("semesterID", semesterID);
        return studentRequestMapper.selectList(queryWrapper);
    }

    @Override
    public void createStudentRequest(@NotNull StudentRequestRequestBody newStudentRequestInfo) {
        if (newStudentRequestInfo.getWeek() <= 0) {
            throw new NumberIllegalException("week<=0！");
        }
        exceptionUtil.TypeException(newStudentRequestInfo.getWeekday(), newStudentRequestInfo.getSlot());
        exceptionUtil.LabIDException(newStudentRequestInfo.getLabID());
        if (!isEntityExists.isSemesterExists(newStudentRequestInfo.getSemesterID())) {
            throw new SemesterNotExistedException("学期不存在！");
        }
        if (!isEntityExists.isStudentExists(newStudentRequestInfo.getStudentID())) {
            throw new StudentNotExistException("学生不存在！");
        }

        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semesterID", newStudentRequestInfo.getSemesterID());
        queryWrapper.eq("week", newStudentRequestInfo.getWeek());
        queryWrapper.eq("weekday", newStudentRequestInfo.getWeekday());
        queryWrapper.eq("slot", newStudentRequestInfo.getSlot());
        queryWrapper.eq("labID", newStudentRequestInfo.getLabID());
        queryWrapper.eq("studentID", newStudentRequestInfo.getStudentID());
        if (studentRequestMapper.exists(queryWrapper)) {
            throw new StudentRequestHasExistedException("请勿重复申请！");
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
        if (!isEntityExists.isStudentRequestExists(updatedStudentRequestInfo.getStudentRequestID())) {
            throw new StudentRequestNotExistException("目标申请不存在，无法更新！");
        }
        if (updatedStudentRequestInfo.getWeek() <= 0) {
            throw new NumberIllegalException("week<=0！");
        }
        exceptionUtil.TypeException(updatedStudentRequestInfo.getWeekday(), updatedStudentRequestInfo.getSlot());
        exceptionUtil.LabIDException(updatedStudentRequestInfo.getLabID());
        if (!isEntityExists.isSemesterExists(updatedStudentRequestInfo.getSemesterID())) {
            throw new SemesterNotExistedException("学期不存在！");
        }
        if (!isEntityExists.isStudentExists(updatedStudentRequestInfo.getStudentID())) {
            throw new StudentNotExistException("学生不存在！");
        }

        UpdateWrapper<StudentRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("studentRequestID", updatedStudentRequestInfo.getStudentRequestID());

        updateWrapper.set("week", updatedStudentRequestInfo.getWeek());
        updateWrapper.set("weekday", updatedStudentRequestInfo.getWeekday());
        updateWrapper.set("slot", updatedStudentRequestInfo.getSlot());

        updateWrapper.set("reason", updatedStudentRequestInfo.getReason());

        // TODO: 2023/5/14 这里没有办法更新status和adminMessage，因为方法参数updatedStudentRequestInfo没有这两个属性
        updateWrapper.set("adminProcessTime", new Timestamp(System.currentTimeMillis()));

        updateWrapper.set("labID", updatedStudentRequestInfo.getLabID());
        updateWrapper.set("semesterID", updatedStudentRequestInfo.getSemesterID());
        updateWrapper.set("studentID", updatedStudentRequestInfo.getStudentID());

        studentRequestMapper.update(null, updateWrapper);
    }

    @Override
    public void setUseComplete(Integer studentRequestID) {
        if (!isEntityExists.isStudentRequestExists(studentRequestID)) {
            throw new StudentRequestNotExistException("目标申请不存在，无法设置申请已完成！");
        }

        UpdateWrapper<StudentRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("studentRequestID", studentRequestID);

        updateWrapper.set("status", StudentRequestStatus.USE_COMPLETE);
        updateWrapper.set("useCompleteTime", new Timestamp(System.currentTimeMillis()));

        studentRequestMapper.update(null, updateWrapper);
    }
}