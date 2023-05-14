package com.example.backend.controller.requestbody;

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
public class StudentRequestRequestBody {
    private Integer studentRequestID;

    // 以下3项是“想要”借用的具体时间段
    private Integer week;
    private Weekday weekday;
    private Slot slot;

    private String reason;

    private Integer labID;  // 想要借用的实验室
    private Integer semesterID; // 想要借用的学期的编号，默认当前学期
    private Integer studentID;  // 发起借用的学生
}
