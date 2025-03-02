package com.aman.SpringSecurity.SpringSecurity.DTO;

import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Permissions;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String name;
    private Set<Role> roles;
}
