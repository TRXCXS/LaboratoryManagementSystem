package com.example.backend.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Technician {
    @TableId
    private Integer technicianID; // 外键，指向User
    private String name;
    private String title;
}
