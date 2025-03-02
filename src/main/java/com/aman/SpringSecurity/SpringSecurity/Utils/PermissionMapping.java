package com.aman.SpringSecurity.SpringSecurity.Utils;

import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Permissions;
import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Permissions.*;
import static com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Role.*;

public class PermissionMapping {

    private static final Map<Role,Set<Permissions>> map = Map.of(
            USER,Set.of(USER_VIEW,POST_VIEW),
            CREATOR,Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE),
            ADMIN,Set.of(POST_CREATE,USER_UPDATE,POST_UPDATE,USER_DELETE,USER_CREATE,POST_DELETE)
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(Role role){
        return map.get(role).stream()
                .map(permissions -> new SimpleGrantedAuthority(permissions.name()))
                .collect(Collectors.toSet());
    }
}
