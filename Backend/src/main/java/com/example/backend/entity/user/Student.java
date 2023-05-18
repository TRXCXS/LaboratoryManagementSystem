package com.example.backend.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @TableId(value = "studentID")
    private Integer studentID; // 外键，指向User
    private String name;
    private String major;
    private String clazz;
}
