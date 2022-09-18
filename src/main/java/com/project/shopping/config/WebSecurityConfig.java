package com.project.shopping.config;

import com.project.shopping.oauth.CustomOAuth2UserService;
import com.project.shopping.oauth.Oauth2SuccessHandler;
import com.project.shopping.repository.UserRepository;
import com.project.shopping.security.JwtAuthenticationFilter;
import com.project.shopping.security.JwtAuthorizationFilter;
import com.project.shopping.security.Tokenprovider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
//@EnableWebSecurity(debug = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private   Tokenprovider tokenprovider;

    @Autowired
    private Oauth2SuccessHandler successHandler;



    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http

                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // cors 필터 걸기
                .formLogin().disable()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()// preFlight 허횽
                .antMatchers("/shopping/**").authenticated()
                .anyRequest().permitAll();
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(),tokenprovider));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(),tokenprovider,userRepository));
        http.cors();
        http.oauth2Login().loginPage("/login").defaultSuccessUrl("/success").successHandler(successHandler).userInfoEndpoint().userService(oAuth2UserService);
    }
}
