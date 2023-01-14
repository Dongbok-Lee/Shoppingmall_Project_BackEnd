package com.project.shopping.dto.requestDTO.UserRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCheckEmailRequestDTO {
    private String email;

    @Builder
    public UserCheckEmailRequestDTO(String email) {
        this.email = email;
    }
}
