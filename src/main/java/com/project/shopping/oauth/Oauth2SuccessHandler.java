package com.project.shopping.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopping.model.User;
import com.project.shopping.repository.UserRepository;
import com.project.shopping.security.Token;
import com.project.shopping.security.Tokenprovider;
import com.project.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class Oauth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Tokenprovider tokenprovider;

    @Autowired
    private UserRequstMapper userRequstMapper;

    @Autowired
    private  UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("OAuth2login 성공");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        User user = userRequstMapper.user(oAuth2User); // 이메일만 담아서 생성

        String jwttoken = tokenprovider.create(user);
        response.addHeader("Authorization","Bearer "+jwttoken); //토큰을 생성 하고
        String email = (String) oAuth2User.getAttributes().get("email");
        String name = (String) oAuth2User.getAttributes().get("name");
        String password = passwordEncoder.encode(email);


        boolean uu = userRepository.existsByEmail(email);

        if(uu == false){
            User users = User.builder()
                    .email(email)
                    .password(password)
                    .username(name)
                    .address("dasdfkjl").age(100)
                    .roles("ROLE_USER")
                    .nickname("user1").phoneNumber("????").build();
            userService.create(users);

            String targetUrl = makeRediretjoinUrl(jwttoken);
            response.sendRedirect(targetUrl);

        }else{
            String targetUrl = makeRedirectUrl(jwttoken);
            response.sendRedirect(targetUrl);
        }


    }

    private String makeRediretjoinUrl(String token) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/registration/"+token)
                .build().toUriString();
    }

    private String makeRedirectUrl(String jwttoken) {
        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth/"+jwttoken)
                .build().toUriString();
    }


}
