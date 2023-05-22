package com.example.backend.controller.request;

import com.example.backend.controller.requestbody.InstructorRequestRequestBody;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.request.InstructorRequest;
import com.example.backend.service.request.InstructorRequestService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/instructor-request")
@CrossOrigin
public class InstructorRequestController {
    private final InstructorRequestService instructorRequestService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/all")
    public GeneralFormattedResponseBody<List<InstructorRequest>>
    getAllInstructorRequests() {
        return GeneralFormattedResponseBody
                .<List<InstructorRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(instructorRequestService.getAllInstructorRequests())
                .build();
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/unhandled")
    public GeneralFormattedResponseBody<List<InstructorRequest>>
    getUnhandledInstructorRequests() {
        return GeneralFormattedResponseBody
                .<List<InstructorRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(instructorRequestService.getUnhandledInstructorRequests())
                .build();
    }

    @Secured({"ROLE_INSTRUCTOR"})
    @GetMapping("/instructor")
    public GeneralFormattedResponseBody<List<InstructorRequest>>
    getInstructorRequestsByInstructor(@RequestParam Integer instructorID) {
        return GeneralFormattedResponseBody
                .<List<InstructorRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(instructorRequestService
                        .getInstructorRequestsByInstructor(instructorID))
                .build();
    }

    @Secured({"ROLE_INSTRUCTOR"})
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createInstructorRequest(
            @RequestBody
            InstructorRequestRequestBody newInstructorRequestInfo,
            HttpServletResponse response) {
        instructorRequestService
                .createInstructorRequest(newInstructorRequestInfo);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @Secured({"ROLE_INSTRUCTOR"})
    @PutMapping
    public GeneralFormattedResponseBody<Object>
    updateInstructorRequest(
            @RequestBody
            InstructorRequestRequestBody updatedInstructorRequestInfo) {
        instructorRequestService
                .updateInstructorRequest(updatedInstructorRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(null)
                .build();
    }

}
