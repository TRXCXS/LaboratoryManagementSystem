package com.example.backend.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.enumClasses.requestStatus.StudentRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    @TableId(type = IdType.AUTO)
    private Integer studentRequestID;
    private Integer week;
    private Weekday weekday;
    private Slot slot;
    private String reason;
    private Timestamp requestTime;
    private StudentRequestStatus status;
    private Timestamp adminProcessTime;
    private String adminMessage;
    private Timestamp useCompleteTime;
}
