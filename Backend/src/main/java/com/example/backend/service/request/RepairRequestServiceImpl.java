package com.example.backend.service.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.backend.controller.requestbody.RepairRequestRequestBody;
import com.example.backend.entity.request.RepairRequest;
import com.example.backend.exception.model.laboratoryException.LabIsRepairingException;
import com.example.backend.exception.request.repairRequestException.RepairRequestNotExistException;
import com.example.backend.mapper.request.RepairRequestMapper;
import com.example.backend.utils.enumClasses.requestStatus.RepairRequestStatus;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@AllArgsConstructor
public class RepairRequestServiceImpl implements RepairRequestService {
    private final RepairRequestMapper repairRequestMapper;

    @Override
    public List<RepairRequest> getAllRepairRequests() {
        // TODO: 2023/5/12 这里暂时是返回全部的维修记录，后续可能需要修改为“本学期的”。下面根据instructorID查找同理。
        return repairRequestMapper.selectList(null);
    }

    @Override
    public List<RepairRequest> getRepairRequestsByInstructor(Integer instructorID) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instructorID", instructorID);
        return repairRequestMapper.selectList(queryWrapper);
    }

    @Override
    public void setRepairing(Integer repairRequestID) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("repairRequestID", repairRequestID);
        if (!repairRequestMapper.exists(queryWrapper)) {
            throw new RepairRequestNotExistException("目标申请不存在，无法更新！");
        }

        UpdateWrapper<RepairRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("repairRequestID", repairRequestID);
        updateWrapper.set("status", RepairRequestStatus.REPAIRING);
        repairRequestMapper.update(null, updateWrapper);
    }

    @Override
    public void setRepaired(Integer repairRequestID) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("repairRequestID", repairRequestID);
        if (!repairRequestMapper.exists(queryWrapper)) {
            throw new RepairRequestNotExistException("目标申请不存在，无法更新！");
        }

        UpdateWrapper<RepairRequest> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("repairRequestID", repairRequestID);
        updateWrapper.set("status", RepairRequestStatus.REPAIRED);
        repairRequestMapper.update(null, updateWrapper);
    }

    @Override
    public void createRepairRequest(@NotNull RepairRequestRequestBody newRepairRequestInfo) {
        QueryWrapper<RepairRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("labID", newRepairRequestInfo.getLabID());
        queryWrapper.ne("status", RepairRequestStatus.REPAIRED);
        if (!repairRequestMapper.exists(queryWrapper)) {
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
}
