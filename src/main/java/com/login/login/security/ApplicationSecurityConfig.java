package com.login.login.security;

import com.login.login.app_user.UserService;
import com.login.login.jwt.JwtTokenVerifier;
import com.login.login.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index.html", "css/**").permitAll()
                .antMatchers( HttpMethod.POST, "/login").permitAll()
                .antMatchers("/", "/login.html", "/App.js").permitAll()
                .antMatchers("/user").authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(STATELESS)

//                .and()
//                .authorizeRequests()
//                .antMatchers("/", "/index.html", "css/**").permitAll()
//                .antMatchers( "login").permitAll()
//                .antMatchers("/", "/user").permitAll()

                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated();

        //metodos sin JWT ->

//                .and()
//                    .formLogin()
//                    .loginPage("/login.html").permitAll()
//                    .passwordParameter("password")
//                    .usernameParameter("username")
//                    .defaultSuccessUrl("/login.html", true)
//
//
//                .and()
//                    .logout()
//                    .logoutUrl("/logout")
//                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET"))
//                        .clearAuthentication(true)
//                        .invalidateHttpSession(true)
//                        .deleteCookies()
//                        .logoutSuccessUrl("/login.html");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }


}
