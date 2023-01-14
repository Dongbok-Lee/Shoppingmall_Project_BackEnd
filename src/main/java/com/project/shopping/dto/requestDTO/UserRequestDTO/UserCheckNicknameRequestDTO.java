package com.project.shopping.dto.requestDTO.UserRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserCheckNicknameRequestDTO {
    private String nickname;

    @Builder
    public UserCheckNicknameRequestDTO(String nickname) {
        this.nickname = nickname;
    }
}
