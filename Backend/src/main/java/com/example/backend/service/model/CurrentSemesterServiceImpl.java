package com.example.backend.service.model;

import com.example.backend.entity.model.CurrentSemester;
import com.example.backend.mapper.model.CurrentSemesterMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentSemesterServiceImpl implements CurrentSemesterService {
    private final CurrentSemester currentSemester;
    private final CurrentSemesterMapper currentSemesterMapper;
}
