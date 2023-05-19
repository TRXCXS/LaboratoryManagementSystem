package com.example.backend.controller.model;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.model.Semester;
import com.example.backend.service.model.SemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/semester")
@CrossOrigin
public class SemesterController {
    private final SemesterService semesterService;

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
    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createNewSemester(@RequestBody Semester newSemester) {
        semesterService.createNewSemester(newSemester);
        return GeneralFormattedResponseBody.<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }


    @PutMapping("/current")
    public GeneralFormattedResponseBody<Object>
    setCurrentSemester(@RequestBody Integer semesterID) {
        semesterService.setCurrentSemester(semesterID);
        return GeneralFormattedResponseBody.<Object>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("success")
                .data(null)
                .build();
    }

}
