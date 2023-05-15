package com.example.backend.service.model;

import com.example.backend.entity.model.Semester;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public interface CurrentSemesterService {
    /**
     * @return 当前学期的semester对象，包含当前学期的信息
     */
    Semester getCurrentSemester();

    /**
     * @return 当前学期的结束时间，该结束时间不被包括在学期内
     */
    Timestamp getCurrentSemesterEndDate();

    /**
     * @param newCurrentSemesterID 想要设置为当前学期的学期的ID
     */
    void setNewCurrentSemester(Integer newCurrentSemesterID);

}
