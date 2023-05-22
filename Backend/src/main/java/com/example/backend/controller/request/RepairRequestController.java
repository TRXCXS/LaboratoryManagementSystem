package com.example.backend.controller.request;

import com.example.backend.controller.requestbody.RepairRequestRequestBody;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.request.RepairRequest;
import com.example.backend.service.request.RepairRequestService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/repair-request")
@CrossOrigin
public class RepairRequestController {
    private final RepairRequestService repairRequestService;

    @Secured({"ROLE_ADMIN", "ROLE_TECHNICIAN"})
    @GetMapping("/all")
    public GeneralFormattedResponseBody<List<RepairRequest>>
    getAllRepairRequests() {
        return GeneralFormattedResponseBody
                .<List<RepairRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(repairRequestService.getAllRepairRequests())
                .build();
    }

    /**
     * RepairRequestsByInstructor
     * 给教师查看自己提交过的保修
     */
    @Secured({"ROLE_INSTRUCTOR"})
    @GetMapping("/instructor")
    public GeneralFormattedResponseBody<List<RepairRequest>>
    getRepairRequestsByInstructor(@RequestParam Integer instructorID) {
        return GeneralFormattedResponseBody
                .<List<RepairRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(repairRequestService.getRepairRequestsByInstructor(instructorID))
                .build();
    }

    @Secured({"ROLE_INSTRUCTOR"})
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createRepairRequest(
            @RequestBody
            RepairRequestRequestBody newRepairRequestInfo,
            HttpServletResponse response) {
        repairRequestService
                .createRepairRequest(newRepairRequestInfo);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_TECHNICIAN"})
    @PutMapping("/repairing")
    public GeneralFormattedResponseBody<Object>
    setRepairing(@RequestParam Integer repairRequestID) {
        repairRequestService.setRepairing(repairRequestID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_TECHNICIAN"})
    @PutMapping("/repaired")
    public GeneralFormattedResponseBody<Object>
    setRepaired(@RequestParam Integer repairRequestID,
                @RequestParam String message) {
        repairRequestService.setRepaired(repairRequestID, message);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

    /**
     * RepairRequestsByTechnician
     * 让实验员查看自己负责的实验室的保修
     */
    @Secured({"ROLE_TECHNICIAN"})
    @GetMapping("/technician")
    public GeneralFormattedResponseBody<List<RepairRequest>>
    getRepairRequestsByTechnician(@RequestParam Integer technicianID) {
        return GeneralFormattedResponseBody
                .<List<RepairRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(repairRequestService.getRepairRequestsByTechnician(technicianID))
                .build();
    }
}
