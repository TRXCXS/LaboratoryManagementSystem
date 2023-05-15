package com.example.backend.service.model;

import com.example.backend.entity.model.Semester;
import com.example.backend.exception.model.semesterException.SemesterHasExistedException;
import com.example.backend.mapper.model.SemesterMapper;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterMapper semesterMapper;
    private final IsEntityExists isEntityExists;
    private final CurrentSemesterService currentSemesterService;

    @Override
    public List<Semester> getAllSemesters() {
        return semesterMapper.selectList(null);
    }

    @Override
    public void createNewSemester(@NotNull Semester newSemester) {
        if (isEntityExists.isSemesterExistsByYearAndSeason(
                newSemester.getFirstHalfYear(),
                newSemester.getSecondHalfYear(),
                newSemester.getSeason()) ||
                isEntityExists.isSemesterExistsByStartDate(newSemester.getStartDate())) {
            throw new SemesterHasExistedException("学期已经存在，无法创建！");
        }

        semesterMapper.insert(newSemester);
    }

    @Override
    public void setCurrentSemester(Integer semesterID) {

        currentSemesterService.setNewCurrentSemester(semesterID);

    }
}
