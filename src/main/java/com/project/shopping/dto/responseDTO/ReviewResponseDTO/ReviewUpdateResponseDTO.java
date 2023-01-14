package com.project.shopping.dto.responseDTO.ReviewResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewUpdateResponseDTO {
    private int reviewId;
    private  String title;
    private  String content;
    private  String ImgUrl;

    @Builder

    public ReviewUpdateResponseDTO(int reviewId, String title, String content, String imgUrl) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.ImgUrl = imgUrl;
    }
}
