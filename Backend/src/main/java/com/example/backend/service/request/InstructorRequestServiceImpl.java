package com.example.backend.service.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.InstructorRequestRequestBody;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.exception.model.semesterException.SemesterNotExistedException;
import com.example.backend.exception.request.instructorRequestException.InstructorRequestHasExistedException;
import com.example.backend.exception.request.instructorRequestException.InstructorRequestNotExistException;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.mapper.request.InstructorRequestMapper;
import com.example.backend.service.model.CurrentSemesterService;
import com.example.backend.utils.enumClasses.requestStatus.InstructorRequestStatus;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class InstructorRequestServiceImpl implements InstructorRequestService {
    private final InstructorRequestMapper instructorRequestMapper;
    private final IsEntityExists isEntityExists;
    private final ExceptionUtil exceptionUtil;
    private final CurrentSemesterService currentSemesterService;

    @Override
    public List<InstructorRequest> getAllInstructorRequests() {
        // 这是获取当前学期的所有InstructorRequest

        int semesterID = currentSemesterService.getCurrentSemester().getSemesterID();

        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semesterID", semesterID);
        return instructorRequestMapper.selectList(queryWrapper);
    }

    @Override
    public List<InstructorRequest> getUnhandledInstructorRequests() {
        int semesterID = currentSemesterService.getCurrentSemester().getSemesterID();

        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semesterID", semesterID);
        queryWrapper.eq("status", InstructorRequestStatus.NOT_ARRANGED);
        return instructorRequestMapper.selectList(queryWrapper);
    }

    @Override
    public List<InstructorRequest> getInstructorRequestsByInstructor(Integer instructorID) {
        int semesterID = currentSemesterService.getCurrentSemester().getSemesterID();

        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semesterID", semesterID);
        queryWrapper.eq("instructorID", instructorID);
        return instructorRequestMapper.selectList(queryWrapper);
    }

    @Override
    public void createInstructorRequest(@NotNull InstructorRequestRequestBody newInstructorRequestInfo) {
        exceptionUtil.WeekException(newInstructorRequestInfo.getStartWeek(), newInstructorRequestInfo.getEndWeek());
        exceptionUtil.TypeException(newInstructorRequestInfo.getWeekday(), newInstructorRequestInfo.getSlot(), newInstructorRequestInfo.getLabType());
        exceptionUtil.StudentCountException(newInstructorRequestInfo.getStudentCount());
        if (!isEntityExists.isInstructorExists(newInstructorRequestInfo.getInstructorID())) {
            throw new InstructorNotExistException("Instructor不存在！");
        }
        if (!isEntityExists.isSemesterExists(newInstructorRequestInfo.getSemesterID())) {
            throw new SemesterNotExistedException("指定的学期不存在！");
        }
        if (isEntityExists.isInstructorRequestExistsByInstructorRequestRequestBody(
                newInstructorRequestInfo.getLabType(),

                newInstructorRequestInfo.getStartWeek(),
                newInstructorRequestInfo.getEndWeek(),
                newInstructorRequestInfo.getWeekday(),
                newInstructorRequestInfo.getSlot(),

                newInstructorRequestInfo.getStudentClass(),
                newInstructorRequestInfo.getStudentCount(),
                newInstructorRequestInfo.getCourse(),

                newInstructorRequestInfo.getInstructorID(),
                newInstructorRequestInfo.getSemesterID()
        )) {
            throw new InstructorRequestHasExistedException("请勿重复申请！");
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

                InstructorRequestStatus.NOT_ARRANGED, // 创建时request处于未安排的状态
                null,
                null,

                newInstructorRequestInfo.getInstructorID(),
                newInstructorRequestInfo.getSemesterID()
        );
        instructorRequestMapper.insert(instructorRequest);
    }

    @Override
    public void updateInstructorRequest(@NotNull InstructorRequestRequestBody updatedInstructorRequestInfo) {
        if (!isEntityExists.isInstructorRequestExists(updatedInstructorRequestInfo.getInstructorRequestID())) {
            throw new InstructorRequestNotExistException("目标申请不存在，无法更新！");
        }
        exceptionUtil.WeekException(updatedInstructorRequestInfo.getStartWeek(), updatedInstructorRequestInfo.getEndWeek());
        exceptionUtil.TypeException(updatedInstructorRequestInfo.getWeekday(), updatedInstructorRequestInfo.getSlot(), updatedInstructorRequestInfo.getLabType());
        exceptionUtil.StudentCountException(updatedInstructorRequestInfo.getStudentCount());
        if (!isEntityExists.isInstructorExists(updatedInstructorRequestInfo.getInstructorID())) {
            throw new InstructorNotExistException("Instructor不存在！");
        }
        if (!isEntityExists.isSemesterExists(updatedInstructorRequestInfo.getSemesterID())) {
            throw new SemesterNotExistedException("指定的学期不存在！");
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

        // TODO: 2023/5/14 这里没有办法更新adminMessage，因为方法参数updatedInstructorRequestInfo没有这两个属性
        updateWrapper.set("adminProcessTime", new Timestamp(System.currentTimeMillis()));

        updateWrapper.set("instructorID", updatedInstructorRequestInfo.getInstructorID());
        updateWrapper.set("semesterID", updatedInstructorRequestInfo.getSemesterID());

        instructorRequestMapper.update(null, updateWrapper);
    }
}
