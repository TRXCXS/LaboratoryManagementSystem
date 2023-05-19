package com.example.backend.controller.requestbody;

import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabRequestBody3 {
    private Integer startWeek;
    private Integer endWeek;
    private Weekday weekday;
    private Slot slot;
    private LabType labType;
    private Integer studentCount;
}
