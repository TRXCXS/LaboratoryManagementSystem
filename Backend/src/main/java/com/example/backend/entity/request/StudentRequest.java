package com.example.backend.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import com.example.backend.utils.enumClasses.requestStatus.StudentRequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    @TableId(value = "studentRequestID", type = IdType.AUTO)
    private Integer studentRequestID;

    // 以下3项是“想要”借用的具体时间段
    private Integer week;
    private Weekday weekday;
    private Slot slot;

    private String reason;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp requestTime;  // 工单产生时间
    private StudentRequestStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp adminProcessTime; // 管理员更新status的时间
    private String adminMessage; // 管理员留言
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp useCompleteTime;  // 使用完毕时间

    // 外键约束属性
    private Integer labID;  // 想要借用的实验室
    private Integer semesterID; // 想要借用的学期的编号，默认当前学期
    private Integer studentID;  // 发起借用的学生
}
