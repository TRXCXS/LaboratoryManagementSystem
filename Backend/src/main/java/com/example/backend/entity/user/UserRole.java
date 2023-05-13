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
public class UserRole {
    /**
     * 2个属性组成复合主键，在数据库中已被指定
     * 在这里应该无需指定
     * 无需自动递增
     * userID是外键，指向User
     */

    @TableId(type = IdType.AUTO)
    private Integer userRoleID;
    private Integer userID;
    private Role role;
}