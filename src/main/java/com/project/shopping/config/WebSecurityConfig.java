package com.project.shopping.config;

import com.project.shopping.oauth.CustomOAuth2Provider;
import com.project.shopping.oauth.CustomOAuth2UserService;
import com.project.shopping.oauth.Oauth2SuccessHandler;
import com.project.shopping.repository.UserRepository;
import com.project.shopping.security.CustomAuthenticationEntryPoint;
import com.project.shopping.security.JwtAuthenticationFilter;
import com.project.shopping.security.JwtAuthorizationFilter;
import com.project.shopping.security.Tokenprovider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CorsFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
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
                .antMatchers("/api/join").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/join/email-check/*").permitAll()
                .antMatchers("/api/join/nickname-check/*").permitAll()
                .antMatchers("/api/products").permitAll()
                .antMatchers("/api/products").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        http.addFilterBefore(new JwtAuthorizationFilter(tokenprovider, userRepository), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager(),tokenprovider), UsernamePasswordAuthenticationFilter.class);

        http.cors();
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/api/oauth2/authorization")
                .and()
                .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig.baseUri("/api/login/oauth2/code/*"))
                .loginPage("/login").defaultSuccessUrl("/success").successHandler(successHandler).userInfoEndpoint().userService(oAuth2UserService);
    }
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties){

        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull) .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    public ClientRegistration getRegistration(OAuth2ClientProperties oAuth2ClientProperties, String client) {

        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get(client);
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();

        if (clientId == null) {
            return null;
        }

        switch (client){//구글, 페이스북은 제공, 네이버 카카오는 따로 Provider 선언해줘야함
            case "google":
                return CustomOAuth2Provider.GOOGLE.getBuilder(client)
                        .clientId(clientId).clientSecret(clientSecret).build();
            case "facebook":
                return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                        .clientId(clientId).clientSecret(clientSecret).build();
            case "kakao":
                return CustomOAuth2Provider.KAKAO.getBuilder(client)
                        .clientId(clientId)
                        .clientSecret(clientSecret).build();
            case "naver":
                return CustomOAuth2Provider.NAVER.getBuilder(client)
                        .clientId(clientId)
                        .clientSecret(clientSecret).build();
        }
        return null;
    }





}
