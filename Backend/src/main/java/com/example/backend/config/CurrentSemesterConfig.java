package com.example.backend.config;

import com.example.backend.entity.model.CurrentSemester;
import com.example.backend.entity.model.Semester;
import com.example.backend.mapper.model.CurrentSemesterMapper;
import com.example.backend.mapper.model.SemesterMapper;
import com.example.backend.service.model.CurrentSemesterService;
import com.example.backend.service.model.CurrentSemesterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.Calendar;

@Configuration
@RequiredArgsConstructor
public class CurrentSemesterConfig {
    private final CurrentSemesterMapper currentSemesterMapper;
    private final SemesterMapper semesterMapper;

    private static final int QUERY_ID = 1;

    @Bean
    public CurrentSemester currentSemester() {
        CurrentSemester cs = currentSemesterMapper.selectById(QUERY_ID);
        assert cs != null
                : "找不到current_semester表中ID为 " + QUERY_ID + " 的记录";
        return cs;
    }

    @Bean
    public CurrentSemesterService currentSemesterService() {
        CurrentSemester currentSemester = currentSemester();
        Integer semesterID = currentSemester.getCurrentSemesterID();
        Semester semester = semesterMapper.selectById(semesterID);
        assert semester != null
                : "在semester表里找不到当前学期，学期ID：" + semesterID;

        long startTime = semester.getStartDate().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        int numberOfDaysToAdd = semester.getWeekCount() * 7;
        calendar.add(Calendar.DAY_OF_YEAR, numberOfDaysToAdd);
        long endTime = calendar.getTimeInMillis();
        Timestamp currentSemesterEndDate = new Timestamp(endTime);

        CurrentSemesterService csService = CurrentSemesterServiceImpl.builder()
                .currentSemester(currentSemester)
                .semesterInfo(semester)
                .currentSemesterMapper(currentSemesterMapper)
                .semesterMapper(semesterMapper)
                .currentSemesterEndDate(currentSemesterEndDate)
                .currentSemesterQueryID(QUERY_ID)
                .build();
        return csService;
    }
}
