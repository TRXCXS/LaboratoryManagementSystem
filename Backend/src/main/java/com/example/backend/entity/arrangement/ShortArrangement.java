package com.example.backend.entity.arrangement;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortArrangement {
    @TableId(type = IdType.AUTO)
    private Integer shortArrangementID;

    // 以下3项为”实际“安排到的时间段，区分于request中”想要“的时间段
    private Integer week;
    private Weekday weekday;
    private Slot slot;

    // 外键约束属性
    private Integer studentRequestID; // 这一安排所针对的申请
    private Integer labID; // ”实际“安排到的实验室
}
