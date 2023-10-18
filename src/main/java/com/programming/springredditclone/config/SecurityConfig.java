package com.programming.springredditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // here we disable csrf protection, because csrf attacks can mainly occurs when there is session
        // and we are using cookies to authenticate the session
        // as rest apis are stateless and we are using JWT for authentication, we can safely disable this feature
        httpSecurity.csrf().disable()
                .authorizeRequests()  //we will allow all the incoming
                .antMatchers("/api/auth/**") // which are started with /api/auth
                .permitAll()
                .anyRequest()  // and anyRequest which are not matching this should be authenticated
                .authenticated();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
