package com.project.shopping.dto.responseDTO.ReviewResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReviewUserJoinResponseDTO {
    private int reviewId;
    private  String title;

    private  String content;

    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private  String ImgUrl;

    @Builder

    public ReviewUserJoinResponseDTO(int reviewId, String title, String content, LocalDateTime createDate, LocalDateTime modifiedDate, String imgUrl) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.ImgUrl = imgUrl;
    }
}
