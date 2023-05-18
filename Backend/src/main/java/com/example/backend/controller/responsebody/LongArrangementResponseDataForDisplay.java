package com.example.backend.controller.responsebody;

import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LongArrangementResponseDataForDisplay {
    private Integer longArrangementID;

    // 实际排课的时间
    private Integer startWeek;
    private Integer endWeek;
    private Weekday weekday;
    private Slot slot;

    // 实际排课的学生班级和学生人数
    private String studentClass;
    private Integer studentCount;

    // 课程名称
    private String course;
    // 教师名称
    private String instructorName;
    // 实验室ID
    private Integer labID;
    // 实验室房间号
    private Integer labNumber;
}
