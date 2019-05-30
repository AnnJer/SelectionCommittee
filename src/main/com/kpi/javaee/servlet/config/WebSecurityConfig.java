package com.kpi.javaee.servlet.config;


import com.kpi.javaee.servlet.config.filters.TokenAuthenticationFilter;
import com.kpi.javaee.servlet.config.handlers.AuthenticationFailureHandler;
import com.kpi.javaee.servlet.config.handlers.AuthenticationSuccessHandler;
import com.kpi.javaee.servlet.config.handlers.RestAuthenticationEntryPoint;
import com.kpi.javaee.servlet.config.services.AuthService;
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



    private static final String AUTHENTICATE_ENDPOINT = "/sign_in";

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

    @Autowired
    AuthService authService;



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint())
                .and()

                    .csrf().disable()


                    .authorizeRequests()
                    .antMatchers("/user", AUTHENTICATE_ENDPOINT).permitAll()
//                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage(AUTHENTICATE_ENDPOINT)
                    .loginProcessingUrl(AUTHENTICATE_ENDPOINT)
                    .failureHandler(authenticationFailureHandler())
                    .successHandler(authenticationSuccessHandler())
                .and()
                    .logout().permitAll()
                .and()
                    .cors()
                .and()
                    .addFilterBefore(tokenAuthenticationFilter(), BasicAuthenticationFilter.class);

        http.authorizeRequests().anyRequest().permitAll();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}