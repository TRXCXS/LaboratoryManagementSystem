package com.example.backend.controller.request;

import com.example.backend.controller.requestbody.RepairRequestRequestBody;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.request.RepairRequest;
import com.example.backend.service.request.RepairRequestService;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/repair-request")
@CrossOrigin
public class RepairRequestController {
    private final RepairRequestService repairRequestService;

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

    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createRepairRequest(
            @RequestBody
            RepairRequestRequestBody newRepairRequestInfo) {
        repairRequestService
                .createRepairRequest(newRepairRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/repairing")
    public GeneralFormattedResponseBody<Object>
    setRepairing(@RequestParam Integer repairRequestID) {
        repairRequestService.setRepairing(repairRequestID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/repaired")
    public GeneralFormattedResponseBody<Object>
    setRepaired(@RequestParam Integer repairRequestID) {
        repairRequestService.setRepaired(repairRequestID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

}
