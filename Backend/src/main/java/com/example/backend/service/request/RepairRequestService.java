package com.example.backend.service.request;

import com.example.backend.controller.requestbody.RepairRequestRequestBody;
import com.example.backend.entity.request.RepairRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RepairRequestService {
    List<RepairRequest> getAllRepairRequests();

    List<RepairRequest> getRepairRequestsByInstructor(Integer instructorID);


    void setRepairing(Integer repairRequestID);

    void setRepaired(Integer repairRequestID, String message);

    void createRepairRequest(RepairRequestRequestBody newRepairRequestInfo);

    /**
     * 让实验员查看自己所负责实验室的保修
     */
    List<RepairRequest> getRepairRequestsByTechnician(Integer technicianID);
}
