package com.kpi.javaee.servlet.config.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.javaee.servlet.config.dto.SessionDto;
import com.kpi.javaee.servlet.config.dto.UserDto;
import com.kpi.javaee.servlet.entities.SessionsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import com.kpi.javaee.servlet.repos.SessionsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    SessionsRepos sessionsRepos;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        UsersEntity securityUser = (UsersEntity) authentication.getPrincipal();
        SessionsEntity sessionsEntity = SessionsEntity.createSession(securityUser);

        sessionsRepos.save(sessionsEntity);

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = response.getWriter();

        UserDto userDto = new UserDto(
                securityUser.getName(),
                securityUser.getSurname(),
                securityUser.getLastname(),
                securityUser.getLogin(),
                List.of(securityUser.getRole())
        );

        SessionDto sessionDto = new SessionDto(
                sessionsEntity.getToken(), userDto
        );

        mapper.writeValue(writer, sessionDto);
        writer.flush();
    }
}