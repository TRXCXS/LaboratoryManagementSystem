package com.example.backend.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.user.User;
import com.example.backend.entity.user.UserRole;
import com.example.backend.mapper.user.UserMapper;
import com.example.backend.mapper.user.UserRoleMapper;
import com.example.backend.service.user.UserService;
import com.example.backend.utils.enumClasses.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserMapper userMapper;
  private final UserRoleMapper userRoleMapper;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    System.out.println("reached service");
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getLoginID(),
            request.getPassword()
        )
    );
    System.out.println("passed authentication");
    User u = userMapper.selectOne(new QueryWrapper<User>().eq("loginID", request.getLoginID()));
    if(u == null) throw new UsernameNotFoundException("User not found");
    List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
            .eq("userID", u.getUserID()));
    List<Role> roleList = new ArrayList<>();
    for(UserRole ur: userRoleList) roleList.add(ur.getRole());
    Role[] roles = new Role[roleList.size()];
    for(int i = 0; i < roleList.size(); i++) roles[i] = roleList.get(i);
    u.setRole(roles);
    Map<String, String> roleSpecificInfo = userService.getRoleSpecificInfo(roleList, u.getUserID());
    String jwtToken = jwtService.generateToken(u);
    return AuthenticationResponse.builder()
            .userID(u.getUserID())
            .loginID(u.getLoginID())
            .role(u.getRole())
            .roleSpecificInfo(roleSpecificInfo)
            .accessToken(jwtToken)
            .build();
  }
}
