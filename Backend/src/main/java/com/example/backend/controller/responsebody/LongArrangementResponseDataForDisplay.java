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

    // 以下4项为”实际“排课的时间
    private Integer startWeek;
    private Integer endWeek;
    private Weekday weekday;
    private Slot slot;

    // 以下2项为”实际“排课的学生班级和学生人数
    private String studentClass;
    private Integer studentCount;

    // 原本long arrangement没有这其中的某些项
    // 需要帮前端查好发过去
    private String course;
    private String instructorName;
    private Integer labID;
    private Integer labNumber;
}
