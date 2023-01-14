package com.project.shopping.dto.requestDTO.UserRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class UserDeleteRequestDTO {

    @NotBlank
    private  String password;

    @Builder
    public UserDeleteRequestDTO(String password) {
        this.password = password;
    }
}
