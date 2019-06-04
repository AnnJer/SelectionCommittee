package com.kpi.javaee.servlet.config;


import com.kpi.javaee.servlet.config.filters.TokenAuthenticationFilter;
import com.kpi.javaee.servlet.config.handlers.AuthenticationFailureHandler;
import com.kpi.javaee.servlet.config.handlers.AuthenticationSuccessHandler;
import com.kpi.javaee.servlet.config.handlers.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    // Beans connected with translating input and output to JSON
    @Bean
    AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }

    @Bean
    AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler();
    }

    @Bean
    RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()
                    .csrf().disable()
                    .authorizeRequests().anyRequest().permitAll()
                .and()
                    .cors()
                .and()
                    .addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().permitAll();
    }

}