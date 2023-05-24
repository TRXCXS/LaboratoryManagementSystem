package com.example.backend.controller.responsebody;

import com.example.backend.utils.enumClasses.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    private Integer userID;
    private String loginID;
    private Role[] role;
    private Map<String, String> roleSpecificInfo;
}
