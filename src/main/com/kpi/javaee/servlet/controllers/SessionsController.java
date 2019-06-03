package com.kpi.javaee.servlet.controllers;

import com.kpi.javaee.servlet.config.dto.UserDto;
import com.kpi.javaee.servlet.entities.UserEntity;
import com.kpi.javaee.servlet.repos.SessionsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionsController {


    @Autowired
    private SessionsRepos sessionsRepos;


    @GetMapping("/{token}")
    UserDto getUser(
            @PathVariable(name = "token") String token
    ) throws IOException {
        UserEntity user = sessionsRepos.findByToken(token).orElseThrow(IOException::new).getUsersByIdUser();

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getLastname(),
                user.getLogin(),
                new ArrayList<>(user.getRateFactorResultsById()),
                List.of(user.getRole())
        );
    }


}
