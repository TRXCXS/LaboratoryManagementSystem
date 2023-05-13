package com.example.backend.service.request;

import com.example.backend.controller.requestbody.InstructorRequestRequestBody;
import com.example.backend.entity.request.InstructorRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InstructorRequestService {
    List<InstructorRequest> getAllInstructorRequests();

    List<InstructorRequest> getUnhandledInstructorRequests();

    List<InstructorRequest> getInstructorRequestsByInstructor(Integer instructorID);

    void createInstructorRequest(InstructorRequestRequestBody newInstructorRequestInfo);

    void updateInstructorRequest(InstructorRequestRequestBody updatedInstructorRequestInfo);
}
