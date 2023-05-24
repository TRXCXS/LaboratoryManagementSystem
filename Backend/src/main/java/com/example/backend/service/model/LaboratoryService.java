package com.example.backend.service.model;

import com.example.backend.controller.responsebody.LabData;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LaboratoryService {
    boolean isLabArranged(Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, Integer labID);

    // 这个接口用来获取指定时间段指定类型的实验室，对应的是InstructorRequest和LongArrangement
    List<Laboratory> getLabsByTime(Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, LabType labType);

    List<Laboratory> getLabsByType(String labType);

    List<Laboratory> getLabsByCapacity(Integer studentCount);

    // 这个接口用来获取指定时间段指定的实验室，对应的是StudentRequest和ShortArrangement
    Laboratory getLab(Integer week, Weekday weekday, Slot slot, Integer labID);

    // InstructorRequest全部条件都满足对应的接口
    List<Laboratory> getLabs(Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, LabType labType, Integer studentCount);

    /**
     * 返回所有实验室，但是按类型分组
     * LabType有几个类型，这个Map就有几个key
     * 每个key的value是一个链表，包含所有这个类型的实验室，不包含其他类型的实验室
     */
    Map<LabType, List<Laboratory>> getLabsGroupByType();

    Integer convertLabNumberToLabID(String labNumber);

    List<LabData> getAllLaboratories();
}
