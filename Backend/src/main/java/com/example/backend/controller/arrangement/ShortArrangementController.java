package com.example.backend.controller.arrangement;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.arrangement.LongArrangement;
import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.service.arrangement.ShortArrangementService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/short-arrangement")
@CrossOrigin
public class ShortArrangementController {
    private final ShortArrangementService shortArrangementService;

    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createShortArrangement(
            @RequestBody ShortArrangement newShortArrangement,
            HttpServletResponse response) {
        shortArrangementService
                .createShortArrangement(newShortArrangement);
        response.setStatus(HttpServletResponse.SC_CREATED);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }

    @GetMapping("/all")
    public GeneralFormattedResponseBody<List<ShortArrangement>>
    getAllShortArrangements() {
        return GeneralFormattedResponseBody
                .<List<ShortArrangement>>builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(shortArrangementService
                        .getAllShortArrangements())
                .build();
    }
}
