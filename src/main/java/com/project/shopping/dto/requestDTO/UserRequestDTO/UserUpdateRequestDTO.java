package com.project.shopping.dto.requestDTO.UserRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class UserUpdateRequestDTO {
    @NotBlank
    private  String username;
    @NotBlank
    private  String address;
    @NotNull
    private int age;
    @NotBlank
    private String nickname;


    @NotBlank
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private  String phoneNumber;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String postCode;


    @Builder
    public UserUpdateRequestDTO(String username, String address, int age, String nickname, String phoneNumber, String email, String postCode) {
        this.username = username;
        this.address = address;
        this.age = age;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.postCode = postCode;
    }
}
