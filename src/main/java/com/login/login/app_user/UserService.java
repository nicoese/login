package com.login.login.app_user;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    public List<AppUser> getAppUsers() {
        return userRepo.findAll();
    }

    public Optional<AppUser> getOne(Long id) {
        return userRepo.findById(id);
    }

    public AppUser addNewUser(AppUser user) {

        if (userRepo.findByUsername(user.getUsername()).isPresent()) throw new IllegalStateException("username taken");
        if (userRepo.findByEmail(user.getEmail()).isPresent()) throw new IllegalStateException("email taken");

        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public AppUser updateUser(Long id, AppUser user) {

        AppUser newUser = userRepo.findById(id)
                .orElseThrow(() -> new IllegalStateException("user with id " + id + " does not exists"));

        if (user.getEmail() != null
                && user.getEmail().length() > 0
                && !Objects.equals(user.getEmail(), newUser.getEmail())) {

            Optional<AppUser> optionaUser = userRepo.findByEmail(user.getEmail());

            if (optionaUser.isPresent()) throw new IllegalStateException("email taken");

            newUser.setEmail(user.getEmail());
        }
        if (user.getUsername() != null
                && user.getUsername().length() > 0
                && !Objects.equals(user.getUsername(), newUser.getUsername())) {

            if (userRepo.
                    findByUsername(user.getUsername())
                    .isPresent()) throw new IllegalStateException("email taken");

            newUser.setUsername(user.getUsername());
        }
        if (user.getPassword() != null
                && user.getPassword().length() > 0
                && !Objects.equals(user.getPassword(), newUser.getPassword())) {

            newUser.setPassword(user.getPassword());
        }
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        AppUser user = userRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user with username " + username + " does not exists"));

        user.setAuthorities(
                user.getRole()
                        .getGrantedAuthorities());

//      SHOW PERMISSIONS ->

        user
                .getAuthorities()
                .stream()
                .forEach(System.out::println);

        return user;
    }
}
