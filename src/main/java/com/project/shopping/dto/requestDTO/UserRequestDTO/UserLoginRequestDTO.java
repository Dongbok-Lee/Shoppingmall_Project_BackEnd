package com.project.shopping.dto.requestDTO.UserRequestDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserLoginRequestDTO {
    private  String email;
    private  String password;

    @Builder

    public UserLoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
