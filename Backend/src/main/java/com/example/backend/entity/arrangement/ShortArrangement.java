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
    private Integer week;
    private Weekday weekday;
    private Slot slot;
}
