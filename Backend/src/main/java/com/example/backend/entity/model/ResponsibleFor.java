package com.example.backend.entity.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsibleFor {
    @TableId(type = IdType.AUTO)
    private Integer responsibleID;
    private Integer technicianID;
    private Integer labID;
}
