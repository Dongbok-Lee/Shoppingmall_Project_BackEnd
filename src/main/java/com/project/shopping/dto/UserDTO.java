package com.project.shopping.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDTO {
    private String email;
    private String password;
    private  String username;
    private  String address;
    private String postCode;
    private int age;
    private  String nickname;
    private  String phoneNumber;
    private  String token;
    @Builder
    public UserDTO(String email , String username, String address, String postCode, int age, String nickname, String phoneNumber, String token){
        this.email = email;
        this.username =username;
        this.address = address;
        this.postCode = postCode;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber= phoneNumber;
        this.token = token;
    }
}
