package com.example.backend.config;

import com.example.backend.entity.model.CurrentSemester;
import com.example.backend.entity.model.Semester;
import com.example.backend.mapper.model.CurrentSemesterMapper;
import com.example.backend.mapper.model.SemesterMapper;
import com.example.backend.service.model.CurrentSemesterService;
import com.example.backend.service.model.CurrentSemesterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.util.Calendar;

@Configuration
@RequiredArgsConstructor
public class CurrentSemesterConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentSemesterConfig.class);
    private static final int QUERY_ID = 1;
    private final CurrentSemesterMapper currentSemesterMapper;
    private final SemesterMapper semesterMapper;

    @Bean
    public CurrentSemester currentSemester() {
        CurrentSemester cs = currentSemesterMapper.selectById(QUERY_ID);
        assert cs != null
                : "找不到current_semester表中ID为 " + QUERY_ID + " 的记录";
        return cs;
    }

    @Bean
    public Semester semester() {
        CurrentSemester currentSemester = currentSemester();
        Integer semesterID = currentSemester.getCurrentSemesterID();
        Semester semester = semesterMapper.selectById(semesterID);
        assert semester != null
                : "在semester表里找不到当前学期，学期ID：" + semesterID;
        return semester;
    }

    @Bean
    public Timestamp currentSemesterEndDate() {
        Semester semester = semester();
        long startTime = semester.getStartDate().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime);
        int numberOfDaysToAdd = semester.getWeekCount() * 7;
        calendar.add(Calendar.DAY_OF_YEAR, numberOfDaysToAdd);
        long endTime = calendar.getTimeInMillis();
        Timestamp currentSemesterEndDate = new Timestamp(endTime);
        return currentSemesterEndDate;
    }

    @Bean
    public Integer queryID() {
        return Integer.valueOf(QUERY_ID);
    }


    @Bean
    public CurrentSemesterService currentSemesterService() {
        CurrentSemesterService csService = new CurrentSemesterServiceImpl(
                currentSemester(),
                semester(),
                currentSemesterMapper,
                semesterMapper,
                currentSemesterEndDate(),
                queryID()
        );
        return csService;
    }
}
