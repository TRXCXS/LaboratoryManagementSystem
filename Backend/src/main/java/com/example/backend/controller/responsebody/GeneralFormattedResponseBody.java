package com.example.backend.controller.responsebody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralFormattedResponseBody<T> {
    private Integer status;
    private String message;
    private T data;
}
