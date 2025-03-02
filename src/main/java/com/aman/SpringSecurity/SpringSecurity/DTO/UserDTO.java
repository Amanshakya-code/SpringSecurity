package com.aman.SpringSecurity.SpringSecurity.DTO;

import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Permissions;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Role;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import com.aman.SpringSecurity.SpringSecurity.Entity.SubscriptionEntity;
import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private Set<Role> roles;
}
