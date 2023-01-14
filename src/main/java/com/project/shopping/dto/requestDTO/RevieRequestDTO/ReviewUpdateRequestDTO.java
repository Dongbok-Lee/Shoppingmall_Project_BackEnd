package com.project.shopping.dto.requestDTO.RevieRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class ReviewUpdateRequestDTO {
    @NotBlank
    private  String imgUrl;
    @NotBlank
    private  String title;
    @NotBlank
    private  String content;

    @Builder

    public ReviewUpdateRequestDTO(String imgUrl, String title, String content) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.content = content;
    }
}
