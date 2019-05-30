package com.kpi.javaee.servlet.config.filters;

import com.kpi.javaee.servlet.entities.SessionsEntity;
import com.kpi.javaee.servlet.entities.UsersEntity;
import com.kpi.javaee.servlet.repos.SessionsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenAuthenticationFilter extends GenericFilterBean {

    @Autowired
    private SessionsRepos sessionsRepos;

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
            //get and check whether token is valid ( from DB or file wherever you are storing the token)

            SessionsEntity sessionsEntity = sessionsRepos.findByToken(accessToken).orElseThrow(IOException::new);

            //Populate SecurityContextHolder by fetching relevant information using token
            final UsersEntity user = sessionsEntity.getUsersByIdUser();

            final UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        chain.doFilter(request, response);
    }

}