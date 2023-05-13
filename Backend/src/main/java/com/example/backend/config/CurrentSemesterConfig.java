package com.example.backend.config;

import com.example.backend.entity.model.CurrentSemester;
import com.example.backend.mapper.model.CurrentSemesterMapper;
import com.example.backend.service.model.CurrentSemesterService;
import com.example.backend.service.model.CurrentSemesterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CurrentSemesterConfig {
    private final CurrentSemesterMapper currentSemesterMapper;

    @Bean
    public CurrentSemester currentSemester() {
        return currentSemesterMapper.selectById(1);
    }

    @Bean
    public CurrentSemesterService currentSemesterService() {
        CurrentSemesterService css =
                new CurrentSemesterServiceImpl(
                        currentSemester(),
                        currentSemesterMapper
                );
        // todo: do things before return
        return css;
    }
}
