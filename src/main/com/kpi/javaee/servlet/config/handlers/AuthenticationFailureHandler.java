package com.kpi.javaee.servlet.config.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.javaee.servlet.config.dto.FaultDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        FaultDto faultDto = new FaultDto("SPRING-SECURITY-1", exception.getMessage());
        writer.println(mapper.writeValueAsString(faultDto));
    }
}