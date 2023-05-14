package com.example.backend.controller.requestbody;

import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * for posting and putting instructor requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorRequestRequestBody {
    private Integer instructorRequestID;
    private LabType labType;

    // 以下6项为”想要“申请的时间段和学生班级、人数
    private Integer startWeek;
    private Integer endWeek;
    private Weekday weekday;
    private Slot slot;

    // studentClass为班级的描述，有可能是”20计算机1-4班“
    private String studentClass;
    private Integer studentCount;
    private String course;

    private Integer instructorID; // 发起申请的教师，默认登录教师
    private Integer semesterID; // 想要的学期，默认当前学期
}
