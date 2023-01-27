package com.project.shopping.dto.responseDTO.ReviewResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReviewProductJoinResponseDTO {
    private int reviewId;
    private  String title;
    private  String content;

    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private  String userNickname;
    private  String ImgURL;

    @Builder

    public ReviewProductJoinResponseDTO(int reviewId, String title, String content, LocalDateTime createDate,String userNickname,String imgURL, LocalDateTime modifiedDate) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.userNickname = userNickname;
        this.ImgURL = imgURL;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
