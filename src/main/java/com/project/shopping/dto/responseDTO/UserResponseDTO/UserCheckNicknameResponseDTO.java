package com.project.shopping.dto.responseDTO.UserResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCheckNicknameResponseDTO {
    private  String nickname;

    @Builder
    public UserCheckNicknameResponseDTO(String nickname) {
        this.nickname = nickname;
    }
}
