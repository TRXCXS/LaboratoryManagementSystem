package com.example.backend.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator extends User {
    private Integer adminID; // 外键，指向User
    private String name;
}
