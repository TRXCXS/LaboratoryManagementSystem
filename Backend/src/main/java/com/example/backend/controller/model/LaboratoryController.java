package com.example.backend.controller.model;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.model.Laboratory;
import com.example.backend.service.model.LaboratoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/laboratory")
@CrossOrigin
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    @GetMapping("/type")
    public GeneralFormattedResponseBody<List<Laboratory>>
    getLabsByType(@RequestParam String labType) {
        return GeneralFormattedResponseBody
                .<List<Laboratory>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabsByType(labType))
                .build();
    }

    @GetMapping("/capacity")
    public GeneralFormattedResponseBody<List<Laboratory>>
    getLabsByCapacity(@RequestParam Integer studentCount) {
        return GeneralFormattedResponseBody
                .<List<Laboratory>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(laboratoryService.getLabsByCapacity(studentCount))
                .build();
    }

}