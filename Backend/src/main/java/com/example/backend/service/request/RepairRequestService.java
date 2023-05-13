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

    void setRepaired(Integer repairRequestID);

    void createRepairRequest(RepairRequestRequestBody newRepairRequestInfo);
}
