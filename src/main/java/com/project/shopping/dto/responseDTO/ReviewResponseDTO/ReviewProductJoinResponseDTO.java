package com.project.shopping.dto.responseDTO.ReviewResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ReviewProductJoinResponseDTO {
    private int reviewId;
    private  String title;
    private  String content;

    private LocalDate createTime;
    private LocalDate modifiedTime;
    private  String userNicename;
    private  String ImgURL;

    @Builder

    public ReviewProductJoinResponseDTO(int reviewId, String title, String content, LocalDate createTime,String userNicename,String imgURL, LocalDate modifiedTime) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.userNicename = userNicename;
        this.ImgURL = imgURL;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
    }
}
