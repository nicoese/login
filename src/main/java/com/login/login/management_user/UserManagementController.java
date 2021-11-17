package com.login.login.management_user;

import com.login.login.app_user.AppUser;
import com.login.login.app_user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.login.login.app_user.UserRoleAndPermissions.UserRole.ADMIN;

@RestController
@RequestMapping(path = "management/user")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@AllArgsConstructor
public class UserManagementController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<AppUser> getAll(){
        return userService.getAppUsers();
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public Optional<AppUser> getOne(@PathVariable("id") Long id){
        return userService.getOne(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public AppUser addNewUser(@RequestBody AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setRole(ADMIN);
        return userService.addNewUser(user);
    }

    @DeleteMapping(path = "{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping(path = "{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public AppUser updateUser(@PathVariable("id")Long id, @RequestBody AppUser user){
        return userService.updateUser(id, user);
    }
}
