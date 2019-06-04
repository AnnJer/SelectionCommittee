package com.kpi.javaee.servlet.config.filters;

import com.kpi.javaee.servlet.config.dto.SessionDto;
import com.kpi.javaee.servlet.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public class TokenAuthenticationFilter extends GenericFilterBean {

    @Value("${endpoints.auth.sessions}")
    private String GET_SESSION_ENDPOINT;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.security.token-header}")
    private String TOKEN_HEADER_NAME;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException
    {
        final HttpServletRequest httpRequest = (HttpServletRequest)request;

        //extract token from header
        final String accessToken = httpRequest.getHeader(TOKEN_HEADER_NAME);


        if (null != accessToken) {

            SessionDto sessionDto = restTemplate.getForObject(GET_SESSION_ENDPOINT + "/" + accessToken, SessionDto.class);

            System.out.println(sessionDto.getUser().getName());

            Role userRole = Role.parseFromString(sessionDto.getUser().getRoles().get(0));

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(sessionDto.getUser(), null, List.of(userRole));

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request, response);
    }

}