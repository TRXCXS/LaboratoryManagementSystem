package com.example.backend.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @TableId(type = IdType.AUTO)
    private Integer userID; // 系统内的唯一标识符
    private String loginID; // 学号或工号
    private String password;
    private Role[] role;
}
