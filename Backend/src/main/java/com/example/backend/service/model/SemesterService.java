package com.example.backend.service.model;

import com.example.backend.entity.model.Semester;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SemesterService {
    List<Semester> getAllSemesters();

    void createNewSemester(Semester newSemester);

    void setCurrentSemester(Integer semesterID);

    Semester getCurrentSemester();
}
