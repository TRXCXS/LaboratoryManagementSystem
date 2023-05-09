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
public class LongArrangement {
    @TableId(type = IdType.AUTO)
    private Integer longArrangementID;

    // 以下4项为”实际“排课的时间，区别于request中”想要“排课的时间
    private Integer startWeek;
    private Integer endWeek;
    private Weekday weekday;
    private Slot slot;

    // 以下2项为”实际“排课的学生班级和学生人数，由管理员指定，区别于”想要“的
    private String studentClass;
    private Integer studentCount;

    // 外键约束属性
    private Integer instructorRequestID; // 这一安排所针对的申请
    private Integer labID; // ”实际“安排到的实验室
}
