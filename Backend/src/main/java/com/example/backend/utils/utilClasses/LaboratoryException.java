package com.example.backend.utils.utilClasses;

import com.example.backend.exception.enumException.LabTypeNotExistException;
import com.example.backend.exception.enumException.SlotTypeNotExistException;
import com.example.backend.exception.enumException.WeekdayTypeNotExistException;
import com.example.backend.exception.model.laboratoryException.LaboratoryNotExistedException;
import com.example.backend.exception.otherException.NumberIllegalException;
import com.example.backend.exception.user.studentException.StudentCountIllegalException;
import com.example.backend.utils.enumClasses.model.LabType;
import com.example.backend.utils.enumClasses.model.Slot;
import com.example.backend.utils.enumClasses.model.Weekday;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LaboratoryException {
    private static LaboratoryException laboratoryException;

    private final IsEntityExists isEntityExists;

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    @PostConstruct
    public void init() {
        laboratoryException = this;
    }

    public void WeekException(Integer startWeek, Integer endWeek) {
        if (startWeek <= 0) {
            throw new NumberIllegalException("startWeek<=0！");
        }
        if (endWeek <= 0) {
            throw new NumberIllegalException("endWeek<=0！");
        }
    }

    public void TypeException(@NotNull Weekday weekday, Slot slot) {
        if (!EnumUtils.isValidEnum(Weekday.class, weekday.toString())) {
            // 枚举类型不存在，抛出异常
            throw new WeekdayTypeNotExistException("Weekday类型不存在！");
        }
        if (!EnumUtils.isValidEnum(Slot.class, slot.toString())) {
            // 枚举类型不存在，抛出异常
            throw new SlotTypeNotExistException("Slot类型不存在！");
        }
    }

    public void TypeException(@NotNull Weekday weekday, Slot slot, LabType labType) {
        if (!EnumUtils.isValidEnum(Weekday.class, weekday.toString())) {
            // 枚举类型不存在，抛出异常
            throw new WeekdayTypeNotExistException("Weekday类型不存在！");
        }
        if (!EnumUtils.isValidEnum(Slot.class, slot.toString())) {
            // 枚举类型不存在，抛出异常
            throw new SlotTypeNotExistException("Slot类型不存在！");
        }
        if (!EnumUtils.isValidEnum(LabType.class, labType.toString())) {
            // 枚举类型不存在，抛出异常
            throw new LabTypeNotExistException("实验室类型不存在！");
        }
    }

    public void LabIDException(Integer labID) {
        if (!isEntityExists.isLaboratoryExists(labID)) {
            throw new LaboratoryNotExistedException("目标实验室不存在！");
        }
    }

    public void StudentCount(Integer studentCount) {
        if (studentCount <= 0) {
            throw new StudentCountIllegalException("学生人数不合法（<=0）！");
        }
    }
}
