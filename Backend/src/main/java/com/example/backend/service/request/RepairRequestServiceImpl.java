package com.example.backend.service.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.RepairRequestRequestBody;
import com.example.backend.entity.request.RepairRequest;
import com.example.backend.exception.model.laboratoryException.LabIsRepairingException;
import com.example.backend.exception.request.repairRequestException.RepairRequestNotExistException;
import com.example.backend.exception.user.instructorException.InstructorNotExistException;
import com.example.backend.mapper.request.RepairRequestMapper;
import com.example.backend.utils.enumClasses.requestStatus.RepairRequestStatus;
import com.example.backend.utils.utilClasses.ExceptionUtil;
import com.example.backend.utils.utilClasses.IsEntityExists;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class RepairRequestServiceImpl implements RepairRequestService {
    private final RepairRequestMapper repairRequestMapper;
    private final IsEntityExists isEntityExists;
    private final ExceptionUtil exceptionUtil;

    @Override
    public List<RepairRequest> getAllRepairRequests() {
        // TODO: 2023/5/14 这个方法返回所有的维修记录，若要返回“本学期”的维修记录（根据某个时间戳在本学期的时间范围内），需要实现其他接口

        return repairRequestMapper.selectList(null);
    }

    @Override
    public List<RepairRequest> getRepairRequestsByInstructor(Integer instructorID) {
        // TODO: 2023/5/14 这个方法根据instructorID返回所有的维修记录，若要返回“本学期”的维修记录（根据某个时间戳在本学期的时间范围内），需要实现其他接口

        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorID", instructorID);
        return repairRequestMapper.selectList(queryWrapper);
    }

    @Override
    public void setRepairing(Integer repairRequestID) {
        if (!isEntityExists.isRepairRequestExists(repairRequestID)) {
            throw new RepairRequestNotExistException("目标申请不存在，无法更新！");
        }

        UpdateWrapper<RepairRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("repairRequestID", repairRequestID);
        updateWrapper.set("status", RepairRequestStatus.REPAIRING);
        updateWrapper.set("repairStartTime", new Timestamp(System.currentTimeMillis()));
        repairRequestMapper.update(null, updateWrapper);
    }

    @Override
    public void setRepaired(Integer repairRequestID, String message) {
        if (!isEntityExists.isRepairRequestExists(repairRequestID)) {
            throw new RepairRequestNotExistException("目标申请不存在，无法更新！");
        }

        UpdateWrapper<RepairRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("repairRequestID", repairRequestID);
        updateWrapper.set("status", RepairRequestStatus.REPAIRED);
        updateWrapper.set("technicianMessage", message);
        updateWrapper.set("repairEndTime", new Timestamp(System.currentTimeMillis()));
        repairRequestMapper.update(null, updateWrapper);
    }

//    @Override
//    public void setRepaired(Integer repairRequestID) {
//        if (!isEntityExists.isRepairRequestExists(repairRequestID)) {
//            throw new RepairRequestNotExistException("目标申请不存在，无法更新！");
//        }
//
//        UpdateWrapper<RepairRequest> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("repairRequestID", repairRequestID);
//        updateWrapper.set("status", RepairRequestStatus.REPAIRED);
//        updateWrapper.set("repairEndTime", new Timestamp(System.currentTimeMillis()));
//        repairRequestMapper.update(null, updateWrapper);
//    }

    @Override
    public void createRepairRequest(@NotNull RepairRequestRequestBody newRepairRequestInfo) {
        if (!isEntityExists.isInstructorExists(newRepairRequestInfo.getInstructorID())) {
            throw new InstructorNotExistException("Instructor不存在！");
        }
        exceptionUtil.LabIDException(newRepairRequestInfo.getLabID());

        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(Wrapper -> Wrapper
                .eq("status", RepairRequestStatus.NOT_REPAIRED)
                .or()
                .eq("status", RepairRequestStatus.REPAIRING)
        );
        queryWrapper.eq("labID", newRepairRequestInfo.getLabID());
        if (repairRequestMapper.exists(queryWrapper)) {
            throw new LabIsRepairingException("目标实验室正处于维修状态，无需再次申请！");
        }

        RepairRequest repairRequest = new RepairRequest(
                null,
                new Timestamp(System.currentTimeMillis()),
                newRepairRequestInfo.getRequestDescription(),
                RepairRequestStatus.NOT_REPAIRED,

                null,
                null,
                null,

                newRepairRequestInfo.getInstructorID(),
                newRepairRequestInfo.getLabID()
        );
        repairRequestMapper.insert(repairRequest);
    }

    @Override
    public List<RepairRequest> getRepairRequestsByTechnician(Integer technicianID) {
        return null;
    }
}
