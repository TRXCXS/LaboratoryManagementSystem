package com.example.backend.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.enumClasses.requestStatus.InstructorRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorRequest {
    @TableId(type = IdType.AUTO)
    private Integer instructorRequestID;
    private Timestamp requestTime;
    private LabType labType;
    private Integer startWeek;
    private Integer endWeek;
    private Weekday weekday;
    private Slot slot;
    private String course;
    private String studentClass;
    private Integer studentCount;
    private InstructorRequestStatus status;
    private Timestamp adminProcessTime;
    private String adminMessage;
}
