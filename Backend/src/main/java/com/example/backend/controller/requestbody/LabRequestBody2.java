package com.example.backend.controller.requestbody;

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
public class LabRequestBody2 {
    private Integer week;
    private Weekday weekday;
    private Slot slot;
    private Integer labID;
}
