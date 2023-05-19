package com.example.backend.controller.model;

import com.example.backend.controller.requestbody.LabRequestBody1;
import com.example.backend.controller.requestbody.LabRequestBody2;
import com.example.backend.controller.requestbody.LabRequestBody3;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.service.model.LaboratoryService;
import com.example.backend.utils.enumClasses.model.LabType;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/laboratory")
@CrossOrigin
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    @GetMapping("/general/type")
    public GeneralFormattedResponseBody<List<Laboratory>>
    getLabsByType(@RequestParam String labType) {
        return GeneralFormattedResponseBody
                .<List<Laboratory>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabsByType(labType))
                .build();
    }

    @GetMapping("/general/capacity")
    public GeneralFormattedResponseBody<List<Laboratory>>
    getLabsByCapacity(@RequestParam Integer studentCount) {
        return GeneralFormattedResponseBody
                .<List<Laboratory>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabsByCapacity(studentCount))
                .build();
    }

    /**
     * 获得全部实验室数据用于表格展示
     * 按实验室类型分类
     */
    @GetMapping("/for-table/group-by-type")
    public GeneralFormattedResponseBody<Map<LabType, List<Laboratory>>>
    getLabsGroupByType() {
        return GeneralFormattedResponseBody.<Map<LabType, List<Laboratory>>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabsGroupByType())
                .build();
    }


    @GetMapping("/for-instructor-requests/time-and-type")
    public GeneralFormattedResponseBody<List<Laboratory>>
    getLabsByTimeAndType(@RequestBody @NotNull LabRequestBody1 request) {
        return GeneralFormattedResponseBody.<List<Laboratory>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabsByTime(
                        request.getStartWeek(),
                        request.getEndWeek(),
                        request.getWeekday(),
                        request.getSlot(),
                        request.getLabType()
                ))
                .build();
    }

    @GetMapping("/for-student-requests/time-and-id")
    public GeneralFormattedResponseBody<Laboratory>
    getLabByTimeAndID(@RequestBody @NotNull LabRequestBody2 request) {
        return GeneralFormattedResponseBody.<Laboratory>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLab(
                        request.getWeek(),
                        request.getWeekday(),
                        request.getSlot(),
                        request.getLabID()
                ))
                .build();
    }

    @GetMapping("/for-instructor-requests/satisfying-everything")
    public GeneralFormattedResponseBody<List<Laboratory>>
    getLabsSatisfyingEverything(@RequestBody @NotNull LabRequestBody3 request) {
        return GeneralFormattedResponseBody.<List<Laboratory>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabs(
                        request.getStartWeek(),
                        request.getEndWeek(),
                        request.getWeekday(),
                        request.getSlot(),
                        request.getLabType(),
                        request.getStudentCount()
                ))
                .build();
    }
}
