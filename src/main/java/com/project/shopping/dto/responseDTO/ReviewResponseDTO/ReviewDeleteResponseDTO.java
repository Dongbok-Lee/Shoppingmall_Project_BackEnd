package com.project.shopping.dto.responseDTO.ReviewResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewDeleteResponseDTO {
    private int reviewId;
    private  String title;
    private  String content;

    @Builder

    public ReviewDeleteResponseDTO(int reviewId, String title, String content) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
    }
}
