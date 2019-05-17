package com.kpi.javaee.servlet.config.filters;

import com.kpi.javaee.servlet.entities.SessionsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import com.kpi.javaee.servlet.repos.SessionsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class CustomBasicAuthenticationFilter extends BasicAuthenticationFilter {

    @Value("spring.security.token-header")
    private String TOKEN_HEADER_NAME;

    @Autowired
    private SessionsRepos sessionsRepos;

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    public CustomBasicAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void onSuccessfulAuthentication(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response, final Authentication authResult) {

        UsersEntity usersEntity = (UsersEntity) authResult.getPrincipal();
        SessionsEntity sessionsEntity = SessionsEntity.createSession(usersEntity);

        sessionsRepos.save(sessionsEntity);

        response.setHeader(TOKEN_HEADER_NAME , sessionsEntity.getToken());
    }

}