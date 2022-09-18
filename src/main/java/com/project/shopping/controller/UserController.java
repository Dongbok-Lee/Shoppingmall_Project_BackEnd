package com.project.shopping.controller;

import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.ResponseDTO;
import com.project.shopping.dto.UserDTO;
import com.project.shopping.model.User;
import com.project.shopping.security.Tokenprovider;
import com.project.shopping.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class UserController {


    @Autowired
    private UserService userService;
//    @Autowired
//    private Tokenprovider tokenprovider;

//    @PostMapping("join")
//    public String join(@RequestBody User user){
//
//        return "회원 가입 완료 ";
//    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @PostMapping("/join")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO){
        try{
            //System.out.println(userDTO.getPassword());
            //System.out.println(passwordEncoder.encode(userDTO.getPassword()));
            User user = User.builder()
                    .email(userDTO.getEmail())
                    .password(passwordEncoder.encode((userDTO.getPassword())))
                    .username(userDTO.getUsername())
                    .address(userDTO.getAddress()).age(userDTO.getAge())
                    .roles("ROLE_USER")
                    .nickname(userDTO.getNickname()).phoneNumber(userDTO.getPhoneNumber())
                    .postCode(userDTO.getPostCode()).build();
            User registeredUser = userService.create(user);

            UserDTO response = UserDTO.builder().username(registeredUser.getUsername()).email(registeredUser.getEmail())
                    .age(registeredUser.getAge()).address(user.getAddress())
                    .nickname(registeredUser.getNickname()).phoneNumber(registeredUser.getPhoneNumber())
                    .postCode(registeredUser.getPostCode()).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/Oauth/join")
    public ResponseEntity<?> oauthsignup(@RequestBody UserDTO userDTO,Authentication authentication) {
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = userDetails.getUser().getEmail();
        User user = userService.findEmailByUser(email);
        user.setAddress(userDTO.getAddress());
        user.setAge(userDTO.getAge());
        user.setNickname(userDTO.getNickname());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userService.SaveUser(user);

        UserDTO response = UserDTO.builder().username(user.getUsername()).email(user.getEmail())
                .age(user.getAge()).address(user.getAddress())
                .nickname(user.getNickname()).phoneNumber(user.getPhoneNumber()).build();
        return ResponseEntity.ok().body(response);


    }

    @GetMapping("/user/info")
    public ResponseEntity<?> userinfo(Authentication authentication){
        try{
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();

            User user = userService.findEmailByUser(email); // 유저 찾기

            UserDTO response = UserDTO.builder().username(user.getUsername()).email(user.getEmail())
                    .age(user.getAge()).address(user.getAddress())
                    .nickname(user.getNickname()).phoneNumber(user.getPhoneNumber()).postCode(user.getPostCode()).build();
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @PutMapping("/user/update")
    public ResponseEntity<?> userupdate(Authentication authentication, @RequestBody UserDTO userDTO){
        try{
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email); // 해당 user찾기
            if(userDTO.getUsername()!= ""){
                user.setUsername(userDTO.getUsername());
            }
            if(userDTO.getAddress() != ""){
                user.setAddress(userDTO.getAddress());
            }

            if(userDTO.getPostCode() != "")
                user.setPostCode(userDTO.getPostCode());

            if(userDTO.getNickname() != ""){
                user.setNickname(userDTO.getNickname());
            }
            if(userDTO.getPhoneNumber()!= ""){
                user.setPhoneNumber(userDTO.getPhoneNumber());
            }

            User updateUser = userService.updateUser(user);
            UserDTO response = UserDTO.builder().username(updateUser.getUsername()).email(updateUser.getEmail())
                    .age(updateUser.getAge()).address(updateUser.getAddress())
                    .nickname(updateUser.getNickname()).phoneNumber(updateUser.getPhoneNumber()).postCode(updateUser.getPostCode()).build();
            return  ResponseEntity.ok().body(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
