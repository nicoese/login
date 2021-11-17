package com.login.login.app_user.UserRoleAndPermissions;
import com.google.common.collect.Lists;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

import static com.login.login.app_user.UserRoleAndPermissions.UserPermission.*;
import static com.login.login.app_user.UserRoleAndPermissions.UserPermission.USER_WRITE;

public enum UserRole {
    USER(Lists.newArrayList()),
    ADMIN(Lists.newArrayList(USER_READ, USER_WRITE));

    private List<UserPermission> permissions;

    UserRole(List<UserPermission> permissions){
        this.permissions = permissions;
    }

    public List<UserPermission> getPermissions(){
        return permissions;
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorities(){
        List<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}
