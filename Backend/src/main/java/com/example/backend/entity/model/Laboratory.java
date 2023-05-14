package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.LabType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Laboratory {
    @TableId(value = "labID", type = IdType.AUTO)
    private Integer labID;
    private String labNumber; // 实际房间号
    private String name;
    private LabType labType;
    private Integer deviceCount;
}
