package com.example.backend.controller.responsebody;

import com.example.backend.utils.enumClasses.model.LabType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabData {
    // 系统内标识符
    private Integer labID;
    // 实际房间号
    private String labNumber;
    private String name;
    private LabType labType;
    private Integer deviceCount;
    // 负责该实验室的实验员姓名列表
    private List<String> technicians;
}
