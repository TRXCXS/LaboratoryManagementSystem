package com.example.backend.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend.entity.user.User;
import com.example.backend.entity.user.UserRole;
import com.example.backend.mapper.user.UserMapper;
import com.example.backend.mapper.user.UserRoleMapper;
import com.example.backend.utils.enumClasses.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User u = userMapper.selectOne(new QueryWrapper<User>().eq("loginID", username));
            if(u == null) throw new UsernameNotFoundException("User not found");
            List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                    .eq("userID", u.getUserID()));
            List<Role> roleList = new ArrayList<>();
            for(UserRole ur: userRoleList) roleList.add(ur.getRole());
            Role[] roles = new Role[roleList.size()];
            for(int i = 0; i < roleList.size(); i++) roles[i] = roleList.get(i);
            u.setRole(roles);
            return u;
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
