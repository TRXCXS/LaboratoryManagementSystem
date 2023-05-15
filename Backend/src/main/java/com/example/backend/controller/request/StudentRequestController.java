package com.example.backend.controller.request;

import com.example.backend.controller.requestbody.StudentRequestRequestBody;
import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.request.StudentRequest;
import com.example.backend.service.request.StudentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/student-request")
@CrossOrigin
public class StudentRequestController {
    private final StudentRequestService studentRequestService;

    @GetMapping("/student")
    public GeneralFormattedResponseBody<List<StudentRequest>>
    getStudentRequestsByStudent(@RequestParam Integer studentID) {
        return GeneralFormattedResponseBody
                .<List<StudentRequest>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(studentRequestService
                        .getStudentRequestsByStudent(studentID))
                .build();
    }

    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createStudentRequest(
            @RequestBody
            StudentRequestRequestBody newStudentRequestInfo) {
        studentRequestService
                .createStudentRequest(newStudentRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping
    public GeneralFormattedResponseBody<Object>
    updateStudentRequest(
            @RequestBody
            StudentRequestRequestBody updatedStudentRequestInfo) {
        studentRequestService
                .updateStudentRequest(updatedStudentRequestInfo);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

    @PutMapping("/use-complete")
    public GeneralFormattedResponseBody<Object>
    setUseComplete(@RequestParam Integer studentRequestID) {
        studentRequestService
                .setUseComplete(studentRequestID);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }
}