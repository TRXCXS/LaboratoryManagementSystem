package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Season;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Semester {
    @TableId(value = "semesterID", type = IdType.AUTO)
    private Integer semesterID;
    private Integer firstHalfYear;
    private Integer secondHalfYear;
    private Season season;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp startDate;
    private Integer weekCount;

    public Semester() {
    }

    public Semester(Integer semesterID, Integer firstHalfYear, Integer secondHalfYear, Season season, Timestamp startDate, Integer weekCount) {
        this.semesterID = semesterID;
        this.firstHalfYear = firstHalfYear;
        this.secondHalfYear = secondHalfYear;
        this.season = season;
        this.startDate = startDate;
        this.weekCount = weekCount;
    }
}
