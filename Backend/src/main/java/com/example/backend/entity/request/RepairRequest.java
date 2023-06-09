package com.example.backend.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.requestStatus.RepairRequestStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class RepairRequest {
    @TableId(value = "repairRequestID",type = IdType.AUTO)
    private Integer repairRequestID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp requestTime;  // 工单产生时间
    private String requestDescription;
    private RepairRequestStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp repairStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp repairEndTime;
    private String technicianMessage;

    // 外键约束属性
    private Integer instructorID; // 发起保修的教师
    private Integer labID; // 待维修设备所在实验室
}
