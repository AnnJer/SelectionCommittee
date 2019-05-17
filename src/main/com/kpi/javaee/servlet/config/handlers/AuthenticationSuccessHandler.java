package com.kpi.javaee.servlet.config.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.javaee.servlet.config.dto.UserDto;
import com.kpi.javaee.servlet.entities.UsersEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        UsersEntity securityUser = (UsersEntity) authentication.getPrincipal();
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = response.getWriter();

        UserDto userDto = new UserDto(
                securityUser.getName(),
                securityUser.getSurname(),
                securityUser.getLastname(),
                securityUser.getLogin(),
                List.of(securityUser.getRole())
        );
        mapper.writeValue(writer, userDto);
        writer.flush();
    }
}