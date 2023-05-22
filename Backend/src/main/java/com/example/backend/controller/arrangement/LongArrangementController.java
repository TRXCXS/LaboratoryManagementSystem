package com.example.backend.controller.arrangement;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.controller.responsebody.LongArrangementResponseDataForDisplay;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.service.arrangement.LongArrangementService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/long-arrangement")
@CrossOrigin
public class LongArrangementController {
    private final LongArrangementService longArrangementService;

    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createLongArrangement(
            @RequestBody LongArrangement newLongArrangement,
            HttpServletResponse response) {
        longArrangementService
                .createLongArrangement(newLongArrangement);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }


    @GetMapping("/table")
    public GeneralFormattedResponseBody<List<LongArrangementResponseDataForDisplay>>
    getTableData() {
        return GeneralFormattedResponseBody.<List<LongArrangementResponseDataForDisplay>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(longArrangementService.getTableData())
                .build();
    }

    @Secured({"ROLE_ADMIN", "ROLE_INSTRUCTOR","ROLE_TECHNICIAN", "ROLE_STUDENT"})
    @GetMapping("/all")
    public GeneralFormattedResponseBody<List<LongArrangement>>
    getAllLongArrangements() {
        return GeneralFormattedResponseBody
                .<List<LongArrangement>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(longArrangementService
                        .getAllLongArrangements())
                .build();
    }
}
