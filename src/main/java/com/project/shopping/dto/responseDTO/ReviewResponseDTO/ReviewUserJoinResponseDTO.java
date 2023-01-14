package com.project.shopping.dto.responseDTO.ReviewResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class ReviewUserJoinResponseDTO {
    private int reviewId;
    private  String title;

    private  String content;

    private LocalDate createTime;
    private LocalDate modifiedTime;
    private  String ImgUrl;

    @Builder

    public ReviewUserJoinResponseDTO(int reviewId, String title, String content, LocalDate createTime, LocalDate modifiedTime, String imgUrl) {
        this.reviewId = reviewId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.modifiedTime = modifiedTime;
        this.ImgUrl = imgUrl;
    }
}
