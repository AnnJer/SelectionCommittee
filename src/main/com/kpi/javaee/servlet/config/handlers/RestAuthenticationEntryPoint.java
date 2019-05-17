package com.kpi.javaee.servlet.config.handlers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpi.javaee.servlet.config.dto.FaultDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = httpServletResponse.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        FaultDto faultDto = new FaultDto("SPRING-SECURITY-1", e.getMessage());
        writer.println(mapper.writeValueAsString(faultDto));
    }
}