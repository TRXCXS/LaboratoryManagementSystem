package com.example.backend.controller.requestbody;

import com.example.backend.utils.enumClasses.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestBodyForUpdate {
    private String userID;
    private String loginID;
    private List<Role> roles;
    private Map<String, String> roleSpecificInfo;
}
