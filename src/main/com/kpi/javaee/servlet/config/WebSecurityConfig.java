package com.kpi.javaee.servlet.config;


import com.kpi.javaee.servlet.config.filters.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                    csrf().disable()
                    .authorizeRequests().anyRequest().permitAll()
                .and()
                    .cors()
                .and()
                    .addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().permitAll();
    }

}