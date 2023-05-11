package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semester {
    @TableId(type = IdType.AUTO)
    private Integer semesterID;
    private Integer firstHalfYear;
    private Integer secondHalfYear;
    private Season season;
    private Timestamp startDate;
    private Integer weekCount;
}
