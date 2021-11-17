package com.login.login.app_user;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.login.login.app_user.UserRoleAndPermissions.UserRole.*;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class userController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public List<AppUser> getAll(Authentication authentication){
        System.out.println(authentication);
        return userService.getAppUsers();
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Optional<AppUser> getOne(@PathVariable("id") Long id){
        return userService.getOne(id);
    }

    @PostMapping
    public AppUser addNewUser(@RequestBody AppUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setRole(USER);
        return userService.addNewUser(user);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public void deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public AppUser updateUser(@PathVariable("id")Long id, @RequestBody AppUser user){
        return userService.updateUser(id, user);
    }
}
