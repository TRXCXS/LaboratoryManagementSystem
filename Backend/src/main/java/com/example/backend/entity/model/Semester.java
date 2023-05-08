package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semester {
    @TableId(type = IdType.AUTO)
    private Integer semesterID;
    private Integer firstHalfYear;
    private Integer secondHalfYear;
    private Season season;
    private Date startDate;
    private Integer weekCount;
}
