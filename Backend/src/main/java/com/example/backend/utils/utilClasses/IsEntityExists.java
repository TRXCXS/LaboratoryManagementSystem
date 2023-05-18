package com.example.backend.utils.utilClasses;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.entity.model.ResponsibleFor;
import com.example.backend.entity.model.Semester;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.entity.request.RepairRequest;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.entity.user.*;
import com.example.backend.mapper.arrangement.LongArrangementMapper;
import com.example.backend.mapper.arrangement.ShortArrangementMapper;
import com.example.backend.mapper.model.LaboratoryMapper;
import com.example.backend.mapper.model.ResponsibleForMapper;
import com.example.backend.mapper.model.SemesterMapper;
import com.example.backend.mapper.request.InstructorRequestMapper;
import com.example.backend.mapper.request.RepairRequestMapper;
import com.example.backend.mapper.request.StudentRequestMapper;
import com.example.backend.mapper.user.*;
import com.example.backend.utils.enumClasses.model.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@AllArgsConstructor
public class IsEntityExists {
    // 判断各个实体类是否存在
    private static IsEntityExists isEntityExists;

    private final LongArrangementMapper longArrangementMapper;
    private final ShortArrangementMapper shortArrangementMapper;

    private final LaboratoryMapper laboratoryMapper;
    private final ResponsibleForMapper responsibleForMapper;
    private final SemesterMapper semesterMapper;

    private final InstructorRequestMapper instructorRequestMapper;
    private final RepairRequestMapper repairRequestMapper;
    private final StudentRequestMapper studentRequestMapper;

    private final AdministratorMapper administratorMapper;
    private final InstructorMapper instructorMapper;
    private final StudentMapper studentMapper;
    private final TechnicianMapper technicianMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;


    @PostConstruct
    public void init() {
        isEntityExists = this;
    }

    public boolean isLongArrangementExists(Integer longArrangementID) {
        QueryWrapper<LongArrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("longArrangementID", longArrangementID);
        if (longArrangementMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isShortArrangementExists(Integer shortArrangementID) {
        QueryWrapper<ShortArrangement> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shortArrangementID", shortArrangementID);
        if (shortArrangementMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLaboratoryExists(Integer labID) {
        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labID", labID);
        if (laboratoryMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLaboratoryExistsByLabNumber(Integer labNumber) {
        QueryWrapper<Laboratory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labNumber", labNumber);
        if (laboratoryMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isResponsibleForExists(Integer technicianID, Integer labID) {
        QueryWrapper<ResponsibleFor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("technicianID", technicianID);
        queryWrapper.eq("labID", labID);
        if (responsibleForMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSemesterExists(Integer semesterID) {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("semesterID", semesterID);
        if (semesterMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSemesterExistsByYearAndSeason(Integer firstHalfYear, Integer secondHalfYear, Season season) {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("firstHalfYear", firstHalfYear);
        queryWrapper.eq("secondHalfYear", secondHalfYear);
        queryWrapper.eq("season", season);
        if (semesterMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isSemesterExistsByStartDate(Timestamp startDate) {
        QueryWrapper<Semester> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("startDate", startDate);
        if (semesterMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInstructorRequestExists(Integer instructorRequestID) {
        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorRequestID", instructorRequestID);
        if (instructorRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInstructorRequestExistsByInstructorID(Integer instructorID) {
        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorID", instructorID);
        if (instructorRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInstructorRequestExistsByInstructorRequestRequestBody(LabType labType, Integer startWeek, Integer endWeek, Weekday weekday, Slot slot, String studentClass, Integer studentCount, String course, Integer instructorID, Integer semesterID) {
        QueryWrapper<InstructorRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labType", labType);
        queryWrapper.eq("startWeek", startWeek);
        queryWrapper.eq("endWeek", endWeek);
        queryWrapper.eq("weekday", weekday);
        queryWrapper.eq("slot", slot);
        queryWrapper.eq("studentClass", studentClass);
        queryWrapper.eq("studentCount", studentCount);
        queryWrapper.eq("course", course);
        queryWrapper.eq("instructorID", instructorID);
        queryWrapper.eq("semesterID", semesterID);
        if (instructorRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRepairRequestExists(Integer repairRequestID) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("repairRequestID", repairRequestID);
        if (repairRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRepairRequestExistsByInstructorID(Integer instructorID) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorID", instructorID);
        if (repairRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRepairRequestExistsByLabID(Integer labID) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labID", labID);
        if (repairRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStudentRequestExists(Integer studentRequestID) {
        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentRequestID", studentRequestID);
        if (studentRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStudentRequestExistsByStudentID(Integer studentID) {
        QueryWrapper<StudentRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentID", studentID);
        if (studentRequestMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserExists(Integer userID) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID", userID);
        if (userMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserExistsByLoginID(String loginID) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginID", loginID);
        if (userMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdministratorExists(Integer adminID) {
        return isUserExists(adminID);
    }

    public boolean isAdministratorExistsByName(String name) {
        QueryWrapper<Administrator> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        if (administratorMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isInstructorExists(Integer instructorID) {
        return isUserExists(instructorID);
    }

    public boolean isInstructorExistsByName(String name) {
        QueryWrapper<Instructor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        if (instructorMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStudentExists(Integer studentID) {
        return isUserExists(studentID);
    }

    public boolean isStudentExistsByName(String name) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        if (studentMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isTechnicianExists(Integer technicianID) {
        return isUserExists(technicianID);
    }

    public boolean isTechnicianExistsByName(String name) {
        QueryWrapper<Technician> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);
        if (technicianMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserRoleExists(Integer userID, Role role) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userID", userID);
        queryWrapper.eq("role", role);
        if (userRoleMapper.exists(queryWrapper)) {
            return true;
        } else {
            return false;
        }
    }
}
