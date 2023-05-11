package com.example.backend.testUtils.enumClasses.Season;

import com.example.backend.utils.enumClasses.model.Season;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void testSeason() {
        Season season = Season.FALL;
        System.out.println(season.getValue());
        season = Season.SPRING;
        System.out.println(Season.getSeasonValue(season));
    }

}