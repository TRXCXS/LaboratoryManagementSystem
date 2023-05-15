package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentSemester {
    @TableId(type = IdType.AUTO)
    private Integer queryID;
    private Integer currentSemesterID;
}
