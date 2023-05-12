package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Season;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentSemester {
    @TableId(type = IdType.AUTO)
    private Integer currentSemesterID;

    @TableField(exist = false)
    private Integer firstHalfYear;
    @TableField(exist = false)
    private Integer secondHalfYear;
    @TableField(exist = false)
    private Season season;
    @TableField(exist = false)
    private Timestamp startDate;
    @TableField(exist = false)
    private Integer weekCount;
    @TableField(exist = false)
    private Timestamp endDate;
}
