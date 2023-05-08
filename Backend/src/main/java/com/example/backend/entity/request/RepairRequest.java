package com.example.backend.entity.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.requestStatus.RepairRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor


public class RepairRequest {
    @TableId(type = IdType.AUTO)
    private Integer repairRequestID;
    private Timestamp requestTime;
    private String requestDescription;
    private RepairRequestStatus status;
    private Timestamp repairStartTime;
    private Timestamp repairEndTime;
    private String technicianMessage;
}
