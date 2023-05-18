package com.example.backend.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.backend.utils.enumClasses.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @TableId(value = "userID",type = IdType.AUTO)
    private Integer userID; // 系统内的唯一标识符
    private String loginID; // 学号或工号
    private String password;
    @TableField(exist = false)
    private Role[] role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roleList = Arrays.asList(role);
        List<SimpleGrantedAuthority> retList = new ArrayList<>();
        for(Role r : roleList) {
            retList.add(new SimpleGrantedAuthority(r.name()));
        }
        return retList;
    }

    @Override
    public String getUsername() {
        return loginID;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
