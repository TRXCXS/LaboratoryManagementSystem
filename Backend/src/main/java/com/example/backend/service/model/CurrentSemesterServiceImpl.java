package com.example.backend.service.model;

import com.example.backend.entity.model.CurrentSemester;
import com.example.backend.entity.model.Semester;
import com.example.backend.exception.model.semesterException.SetCurrentSemesterException;
import com.example.backend.mapper.model.CurrentSemesterMapper;
import com.example.backend.mapper.model.SemesterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class CurrentSemesterServiceImpl implements CurrentSemesterService {
    private final CurrentSemesterMapper currentSemesterMapper;
    private final SemesterMapper semesterMapper;
    private final Integer currentSemesterQueryID;
    private CurrentSemester currentSemester;
    private Semester semesterInfo;
    private Timestamp currentSemesterEndDate;

    public CurrentSemesterServiceImpl(
            CurrentSemester currentSemester,
            Semester semester,
            CurrentSemesterMapper currentSemesterMapper,
            SemesterMapper semesterMapper,
            Timestamp currentSemesterEndDate,
            Integer queryID) {
        this.currentSemester = currentSemester;
        this.semesterInfo = semester;
        this.currentSemesterMapper = currentSemesterMapper;
        this.semesterMapper = semesterMapper;
        this.currentSemesterEndDate = currentSemesterEndDate;
        this.currentSemesterQueryID = queryID;
    }

    @Override
    public Semester getCurrentSemester() {
        return semesterInfo;
    }

    @Override
    public Timestamp getCurrentSemesterEndDate() {
        return currentSemesterEndDate;
    }

    @Override
    @Transactional
    public void setNewCurrentSemester(Integer newCurrentSemesterID) {
        CurrentSemester toUpdate = new CurrentSemester(
                currentSemesterQueryID, newCurrentSemesterID);
        int ret = currentSemesterMapper.updateById(toUpdate);
        if (ret != 1) throw new SetCurrentSemesterException("在表current_semester设置当前学期失败");

        semesterInfo = semesterMapper.selectById(newCurrentSemesterID);
        if (semesterInfo == null) throw new SetCurrentSemesterException("在表semester无法找到指定学期");

        long startTime = semesterInfo.getStartDate().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        int numberOfDaysToAdd = semesterInfo.getWeekCount() * 7;
        calendar.add(Calendar.DAY_OF_YEAR, numberOfDaysToAdd);
        long endTime = calendar.getTimeInMillis();
        currentSemesterEndDate = new Timestamp(endTime);
    }

}
