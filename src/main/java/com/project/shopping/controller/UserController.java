package com.project.shopping.controller;


import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.auth.PrincipalDetails;

import com.project.shopping.dto.ResponseDTO;
import com.project.shopping.dto.UserDTO;
import com.project.shopping.dto.requestDTO.UserRequestDTO.*;

import com.project.shopping.dto.responseDTO.UserResponseDTO.*;
import com.project.shopping.model.User;
import com.project.shopping.service.UserService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@RestController
@Slf4j

@RequiredArgsConstructor
public class UserController  {


    private final  UserService userService;

    @PostMapping("/api/join")
    public ResponseEntity<?> signup(@RequestBody @Valid userJoinRequestDTO userJoinRequestDTO){

        UserJoinResponseDTO userJoinResponseDTO = userService.create(userJoinRequestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("msg", "회원가입에 성공했습니다.");
        response.put("data", userJoinResponseDTO);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/api/user/delete")
    public ResponseEntity<?> deleteUser(@RequestBody @Valid UserDeleteRequestDTO userDeleteRequestDTO, Authentication authentication ){


        UserDeleteResponseDTO userDeleteResponseDTO  = userService.delete(userDeleteRequestDTO,authentication);


        Map<String, Object> response = new HashMap<>();
        response.put("msg", "회원탈퇴에 성공했습니다.");
        response.put("data", userDeleteResponseDTO);
        return  ResponseEntity.ok().body(response);



    }

    @PostMapping("/api/Oauth/join")
    public ResponseEntity<?> oauthsignup(@RequestBody UserDTO userDTO,Authentication authentication) {
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = userDetails.getUser().getEmail();
        System.out.println(email);

        UserDTO response = userService.SaveUser(userDTO, authentication);


        return ResponseEntity.ok().body(response);


    }


//    @PostMapping("/Oauth/join")
//    public ResponseEntity<?> oauthsignup(@RequestBody UserDTO userDTO) {
//
//        try{
//            //System.out.println(userDTO.getPassword());
//            //System.out.println(passwordEncoder.encode(userDTO.getPassword()));
//            String password = passwordEncoder.encode(userDTO.getEmail());
//            User user = User.builder()
//                    .email(userDTO.getEmail())
//                    .password(password)
//                    .username(userDTO.getUsername())
//                    .address(userDTO.getAddress()).age(userDTO.getAge())
//                    .roles("ROLE_USER")
//                    .nickname(userDTO.getNickname()).phoneNumber(userDTO.getPhoneNumber())
//                    .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                    .status("active")
//                    .postCode(userDTO.getPostCode()).build();
//            User registeredUser = userService.create(user);
//
//            UserDTO response = UserDTO.builder().username(registeredUser.getUsername()).email(registeredUser.getEmail())
//                    .age(registeredUser.getAge()).address(user.getAddress())
//                    .nickname(registeredUser.getNickname()).phoneNumber(registeredUser.getPhoneNumber())
//                    .postCode(registeredUser.getPostCode()).build();
//            return ResponseEntity.ok().body(response);
//        }catch (Exception e){
//            ResponseDTO response = ResponseDTO.builder().error(e.getMessage()).build();
//            return ResponseEntity.badRequest().body(response);
//        }
//
//
//    }

    @GetMapping("/api/user/info")
    public ResponseEntity<?> userinfo(Authentication authentication){
        if(authentication == null){
            throw  new CustomException("허용되지 않은 접근입니다",ErrorCode.UnauthorizedException);
        }


        UserInfoResponseDTO userInfoResponseDTO = userService.findEmailByUser(authentication); // 유저 찾기


        Map<String, Object> response = new HashMap<>();
        response.put("msg", "유저조회에 성공했습니다.");
        response.put("data", userInfoResponseDTO);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/api/user/update")
    public ResponseEntity<?> userupdate(Authentication authentication, @RequestBody @Valid UserUpdateRequestDTO userUpdateRequestDTO){
        if(authentication == null){
            throw  new CustomException("허용되지 않은 접근입니다",ErrorCode.UnauthorizedException);
        }

        UserUpdateResponseDTO userUpdateResponseDTO = userService.updateUser(authentication, userUpdateRequestDTO);

        Map<String, Object> response = new HashMap<>();
        response.put("msg", "유저수정에 성공했습니다.");
        response.put("data", userUpdateResponseDTO);
        return  ResponseEntity.ok().body(response);
    }


    @GetMapping("/api/join/email-check/{value}")
    public ResponseEntity<?> checkemail(@PathVariable(value = "value") String value) throws Exception {
        userService.existsByEmail(value);
        UserCheckEmailResponseDTO userCheckEmailResponseDTO = UserCheckEmailResponseDTO.builder().email(value).build();

        Map<String, Object> response = new HashMap<>();
        response.put("msg", "사용가능한 이메일 입니다..");
        response.put("data", userCheckEmailResponseDTO);
        return ResponseEntity.ok().body(response);




    }

    @GetMapping("/api/join/nickname-check/{value}")
    public ResponseEntity<?> checknickname(@PathVariable(value = "value") String value){
        userService.existsByNickname(value);
        UserCheckNicknameResponseDTO userCheckNicknameResponseDTO = UserCheckNicknameResponseDTO.builder().nickname(value).build();
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "사용가능한 닉네임 입니다..");
        response.put("data", userCheckNicknameResponseDTO);
        return ResponseEntity.ok().body(response);

    }
}
