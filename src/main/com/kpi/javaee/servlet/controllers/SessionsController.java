package com.kpi.javaee.servlet.controllers;

import com.kpi.javaee.servlet.config.dto.UserDto;
import com.kpi.javaee.servlet.entities.UsersEntity;
import com.kpi.javaee.servlet.repos.SessionsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
        UsersEntity user = sessionsRepos.findByToken(token).orElseThrow(IOException::new).getUsersByIdUser();

        return new UserDto(
                user.getName(), user.getSurname(), user.getLastname(), user.getLogin(), List.of(user.getRole())
        );
    }


}
