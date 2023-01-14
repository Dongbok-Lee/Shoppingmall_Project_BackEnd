package com.project.shopping.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.UserDTO;
import com.project.shopping.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends JsonIdPwAuthenticationFilter{
    private  final AuthenticationManager authenticationManager;
    private  final Tokenprovider tokenprovider;
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        try{
            System.out.println("인증완료");
            PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
            User user = principalDetails.getUser();
            String jwt = tokenprovider.create(user);
            System.out.println(jwt);
            response.setContentType("application/json");

            response.addHeader("Authorization","Bearer "+jwt);


//            UserDTO res = UserDTO.builder().username(user.getUsername()).email(user.getEmail())
//                    .age(user.getAge()).address(user.getAddress())
//                    .nickname(user.getNickname()).phoneNumber(user.getPhoneNumber()).build();
            Map<String, Object> res = new HashMap<>();
            res.put("msg", "login success");
            ResponseEntity.ok().body(res);

            String result = objectMapper.writeValueAsString(res);
            response.getWriter().write(result);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("로그인 시도중");
        try{

            User user = objectMapper.readValue(request.getInputStream(),User.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (IOException e) {
            return null;
        }

    }

}
