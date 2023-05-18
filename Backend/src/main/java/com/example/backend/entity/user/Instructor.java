package com.example.backend.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {
    @TableId(value = "instructorID")
    private Integer instructorID; // 外键，指向User
    private String name;
    private String title;
}
