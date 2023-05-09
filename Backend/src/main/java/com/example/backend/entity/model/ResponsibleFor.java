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
    // 2个属性组成复合主键，在数据库中指定
    // 无需自动递增
    // 这样做可以防止同一个责任被重复记录
    private Integer technicianID;
    private Integer labID;
}
