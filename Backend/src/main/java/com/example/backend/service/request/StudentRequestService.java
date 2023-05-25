package com.example.backend.service.request;

import com.example.backend.controller.requestbody.StudentRequestRequestBody;
import com.example.backend.controller.responsebody.StudentRequestResponseData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentRequestService {
    List<StudentRequestResponseData> getStudentRequestsByStudent(Integer studentID);

    void createStudentRequest(StudentRequestRequestBody newStudentRequestInfo);

    void updateStudentRequest(StudentRequestRequestBody updatedStudentRequestInfo);

    void setUseComplete(Integer studentRequestID);

    List<StudentRequestResponseData> getAllStudentRequests();

    List<StudentRequestResponseData> getUnhandledStudentRequests();

    void denyStudentRequest(Integer studentRequestID, String adminMessage);
}
