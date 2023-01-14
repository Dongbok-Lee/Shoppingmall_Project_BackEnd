package com.project.shopping.dto.responseDTO.UserResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDeleteResponseDTO {
    private  String email;
    private  String username;
    private  String nickname;

    @Builder
    public UserDeleteResponseDTO(String email, String username, String nickname) {
        this.email = email;
        this.username = username;
        this.nickname = nickname;
    }
}
