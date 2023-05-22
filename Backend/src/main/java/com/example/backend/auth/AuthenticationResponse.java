package com.example.backend.auth;

import com.example.backend.utils.enumClasses.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private Integer userID;
  private String loginID;
  private Role[] role;
  private Map<String, String> roleSpecificInfo;
  private String accessToken;
}
