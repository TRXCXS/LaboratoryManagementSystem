package com.example.backend.service.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.exception.enumException.LabTypeNotExistException;
import com.example.backend.exception.otherException.NumberIllegalException;
import com.example.backend.mapper.arrangement.LongArrangementMapper;
import com.example.backend.mapper.arrangement.ShortArrangementMapper;
import com.example.backend.mapper.model.LaboratoryMapper;
import com.example.backend.mapper.request.InstructorRequestMapper;
import com.example.backend.mapper.request.StudentRequestMapper;
import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LaboratoryServiceImpl implements LaboratoryService {
    private final LaboratoryMapper laboratoryMapper;
    private final LongArrangementMapper longArrangementMapper;
    private final ShortArrangementMapper shortArrangementMapper;
    private final InstructorRequestMapper instructorRequestMapper;
    private final StudentRequestMapper studentRequestMapper;
    private final ExceptionUtil exceptionUtil;
    private final CurrentSemesterService currentSemesterService;

    @Override
    public boolean isLabArranged(Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, Integer labID) {
        /**
         判断目标实验室是否在本学期的指定时间段内已经被安排了
         */

        exceptionUtil.WeekException(startWeek, endWeek);
        exceptionUtil.TypeException(weekday, slot);
        exceptionUtil.LabIDException(labID);

        QueryWrapper<LongArrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(Wrapper -> Wrapper
                .lt("startWeek", endWeek)
                .or()
                .gt("endWeek", startWeek)
                .or()
                .ge("startWeek", startWeek)
                .le("endWeek", endWeek)
        );
        queryWrapper.eq("weekday", weekday);
        queryWrapper.eq("slot", slot);
        queryWrapper.eq("labID", labID);
        // longArrangementList是该实验室所有的与本次要求的时间相冲突的安排
        List<LongArrangement> longArrangementList = longArrangementMapper.selectList(queryWrapper);

        if (longArrangementList.isEmpty()) {
            // 该实验室与本次要求的时间相冲突的安排在任何学期都不存在
        } else {
            int semesterID = currentSemesterService.getCurrentSemester().getSemesterID();

            for (int i = 0; i < longArrangementList.size(); i++) {
                InstructorRequest instructorRequest = instructorRequestMapper.selectById(longArrangementList.get(i).getInstructorRequestID());
                if (instructorRequest.getSemesterID() == semesterID) ;
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Laboratory> getLabsByTime(Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, @NotNull LabType labType) {
        /**
         找到指定时间段内指定类型的实验室，确保该实验室在该时间段内没有被安排。
         */

        exceptionUtil.WeekException(startWeek, endWeek);
        exceptionUtil.TypeException(weekday, slot, labType);

        // 先找到所有符合类型的实验室，再根据时间的约束条件进行排除
        List<Laboratory> laboratoryList = getLabsByType(labType.toString());
        List<Laboratory> targetLabs = new ArrayList<>();
        for (int i = 0; i < laboratoryList.size(); i++) {
            if (!isLabArranged(startWeek, endWeek, weekday, slot, laboratoryList.get(i).getLabID())) {
                targetLabs.add(laboratoryList.get(i));
            }
        }
        return targetLabs;
    }

    @Override
    public List<Laboratory> getLabsByType(String labType) {
        /**
         找到指定类型的实验室，该实验室可能已经被安排了。
         */

        if (!EnumUtils.isValidEnum(LabType.class, labType)) {
            // 枚举类型不存在，抛出异常
            throw new LabTypeNotExistException("实验室类型不存在！");
        }

        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labType", labType);
        return laboratoryMapper.selectList(queryWrapper);
    }

    @Override
    public List<Laboratory> getLabsByCapacity(Integer studentCount) {
        /**
         找到设备数量大于等于学生人数的实验室，该实验室可能已经被安排了。
         */

        exceptionUtil.StudentCountException(studentCount);

        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("deviceCount", studentCount);
        return laboratoryMapper.selectList(queryWrapper);
    }

    @Override
    public Laboratory getLab(Integer week, Weekday weekday, Slot slot, Integer labID) {
        if (week <= 0) {
            throw new NumberIllegalException("week<=0！");
        }
        exceptionUtil.TypeException(weekday, slot);
        exceptionUtil.LabIDException(labID);

        QueryWrapper<ShortArrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("week", week);
        queryWrapper.eq("weekday", weekday);
        queryWrapper.eq("slot", slot);
        queryWrapper.eq("labID", labID);
        List<ShortArrangement> shortArrangementList = shortArrangementMapper.selectList(queryWrapper);

        if (shortArrangementList.isEmpty()) {
        } else {
            int semesterID = currentSemesterService.getCurrentSemester().getSemesterID();
            for (int i = 0; i < shortArrangementList.size(); i++) {
                StudentRequest studentRequest = studentRequestMapper.selectById(shortArrangementList.get(i).getStudentRequestID());
                if (studentRequest.getSemesterID() == semesterID) ;
                {
                    return null;
                }
            }
        }
        return laboratoryMapper.selectById(labID);
    }

    @Override
    public List<Laboratory> getLabs(Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, LabType labType, Integer studentCount) {
        exceptionUtil.WeekException(startWeek, endWeek);
        exceptionUtil.TypeException(weekday, slot, labType);
        exceptionUtil.StudentCountException(studentCount);

        List<Laboratory> targetLabs = getLabsByTime(startWeek, endWeek, weekday, slot, labType);
        for (int i = 0, len = targetLabs.size(); i < len; i++) {
            // 排除掉设备数量不足的实验室
            if (targetLabs.get(i).getDeviceCount() < studentCount) {
                targetLabs.remove(i);
                len--;
                i--;
            }
        }
        return targetLabs;
    }

    @Override
    public Map<LabType, List<Laboratory>> getLabsGroupByType() {
        return null;
    }
}
