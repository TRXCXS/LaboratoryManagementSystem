package com.example.backend.service.request;

import com.example.backend.controller.requestbody.StudentRequestRequestBody;
import com.example.backend.entity.request.StudentRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentRequestService {
    List<StudentRequest> getStudentRequestsByStudent(Integer studentID);

    void createStudentRequest(StudentRequestRequestBody newStudentRequestInfo);

    void updateStudentRequest(StudentRequestRequestBody updatedStudentRequestInfo);

    void setUseComplete(Integer studentRequestID);
}
