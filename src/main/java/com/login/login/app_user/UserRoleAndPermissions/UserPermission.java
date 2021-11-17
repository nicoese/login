package com.login.login.app_user.UserRoleAndPermissions;

import org.springframework.security.core.GrantedAuthority;

public enum UserPermission implements GrantedAuthority {

    USER_READ("user:read"),
    USER_WRITE("user:write");

    private final String permission;

    UserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public String getAuthority() {
        return getPermission();
    }
}
