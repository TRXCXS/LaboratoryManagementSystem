package com.example.backend.controller.arrangement;

import com.example.backend.controller.responsebody.GeneralFormattedResponseBody;
import com.example.backend.entity.arrangement.ShortArrangement;
import com.example.backend.service.arrangement.ShortArrangementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/short-arrangement")
@CrossOrigin
public class ShortArrangementController {
    private final ShortArrangementService shortArrangementService;

    @PostMapping
    public GeneralFormattedResponseBody<Object>
    createShortArrangement(
            @RequestBody ShortArrangement newShortArrangement) {
        shortArrangementService
                .createShortArrangement(newShortArrangement);
        return GeneralFormattedResponseBody
                .<Object>builder()
                .status(HttpStatus.CREATED.value())
                .message("success")
                .data(null)
                .build();
    }
}
