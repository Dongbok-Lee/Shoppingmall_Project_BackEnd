package com.project.shopping.dto.responseDTO.UserResponseDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class UserJoinResponseDTO {
    private String email;
    private  String username;
    private  String address;
    private String postCode;
    private int age;
    private  String nickname;
    private  String phoneNumber;
    private Timestamp createDate;
    private  Timestamp modifiedDate;


    @Builder

    public UserJoinResponseDTO(String email, String username, String address, String postCode, int age, String nickname, String phoneNumber, Timestamp createDate, Timestamp modifiedDate) {
        this.email = email;
        this.username = username;
        this.address = address;
        this.postCode = postCode;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
