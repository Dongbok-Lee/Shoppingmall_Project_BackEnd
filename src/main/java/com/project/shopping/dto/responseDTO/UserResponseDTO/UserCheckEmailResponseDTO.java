package com.project.shopping.dto.responseDTO.UserResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCheckEmailResponseDTO {
    private  String email;

    @Builder

    public UserCheckEmailResponseDTO(String email) {
        this.email = email;
    }
}
