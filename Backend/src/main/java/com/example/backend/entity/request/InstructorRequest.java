package com.example.backend.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.enumClasses.requestStatus.InstructorRequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRequest {
    @TableId(value = "instructorRequestID", type = IdType.AUTO)
    private Integer instructorRequestID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp requestTime;

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

    private InstructorRequestStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp adminProcessTime;
    private String adminMessage;

    // 外键约束属性
    private Integer instructorID; // 发起申请的教师
    private Integer semesterID; // 想要的学期，默认当前学期
}
