package com.example.backend.controller.requestbody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepairRequestRequestBody {
    private Integer repairRequestID;
    private String requestDescription;
    private Integer instructorID; // 发起保修的教师
    private Integer labID; // 待维修设备所在实验室
}
