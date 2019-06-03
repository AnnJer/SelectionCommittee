package com.kpi.javaee.servlet.config.services;


import com.kpi.javaee.servlet.config.Exceptions.UserNotFoundException;
import com.kpi.javaee.servlet.config.dto.UserDto;
import com.kpi.javaee.servlet.entities.UserEntity;
import com.kpi.javaee.servlet.repos.UsersRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("authService")
public class AuthService implements UserDetailsService {


    @Autowired
    private UsersRepos usersRepos;


    @Override
    public UserEntity loadUserByUsername(String username) {

        return usersRepos.findByLogin(username).orElseThrow(
                () -> new UserNotFoundException(String.format("User %s not found", username))
        );

    }


    public UserDto getLoggedUser() {
        UserEntity securityUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserDto(
                securityUser.getId(),
                securityUser.getName(),
                securityUser.getSurname(),
                securityUser.getLastname(),
                securityUser.getLogin(),
                new ArrayList<>(securityUser.getRateFactorResultsById()),
                List.of(securityUser.getRole())
        );
    }

}