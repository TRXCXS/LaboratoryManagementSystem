package com.example.backend.controller.model;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.model.Semester;
import com.example.backend.service.model.SemesterService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/semester")
@CrossOrigin
public class SemesterController {
    private final SemesterService semesterService;

    @Secured({"ROLE_ADMIN", "ROLE_INSTRUCTOR","ROLE_TECHNICIAN", "ROLE_STUDENT"})
    @GetMapping("/all")
    public GeneralFormattedResponseBody<List<Semester>>
    getAllSemesters() {
        return GeneralFormattedResponseBody.<List<Semester>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(semesterService.getAllSemesters())
                .build();
    }

    /**
     * 创建新学期
     * @param newSemester 其中的semesterID无意义，填0即可
     */
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createNewSemester(@RequestBody Semester newSemester,
                      HttpServletResponse response) {
        semesterService.createNewSemester(newSemester);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody.<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }


    @Secured({"ROLE_ADMIN"})
    @PutMapping("/current")
    public GeneralFormattedResponseBody<Object>
    setCurrentSemester(@RequestBody Integer semesterID,
                       HttpServletResponse response) {
        semesterService.setCurrentSemester(semesterID);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        return GeneralFormattedResponseBody.<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

}
