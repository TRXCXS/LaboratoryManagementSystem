package com.example.backend.controller.model;

import com.example.backend.entity.model.Semester;
import com.example.backend.service.model.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/semester")
@CrossOrigin
public class SemesterController {
    private final SemesterService semesterService;

    @GetMapping("/all")
    public List<Semester> getAllSemesters() {
        return semesterService.getAllSemesters();
    }
}
