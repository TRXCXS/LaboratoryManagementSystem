package com.example.backend.service.model;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.model.Semester;
import com.example.backend.exception.model.semesterException.SemesterHasExistedException;
import com.example.backend.mapper.model.SemesterMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterMapper semesterMapper;

    @Override
    public List<Semester> getAllSemesters() {
        return semesterMapper.selectList(null);
    }

    @Override
    public void createNewSemester(@NotNull Semester newSemester) {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("firstHalfYear", newSemester.getFirstHalfYear());
        queryWrapper.eq("secondHalfYear", newSemester.getSecondHalfYear());
        queryWrapper.eq("season", newSemester.getSeason());
        queryWrapper.or().eq("startDate", newSemester.getStartDate());

        if (semesterMapper.exists(queryWrapper)) {
            throw new SemesterHasExistedException("学期已经存在，无法创建");
        }

        semesterMapper.insert(newSemester);
    }

    @Override
    public void setCurrentSemester(Integer semesterID) {

        // TODO: 2023/5/12 可能需要新建一个currentSemester表，这样就不需要这个接口了

    }
}
