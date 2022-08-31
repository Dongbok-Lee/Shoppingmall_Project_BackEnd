package com.project.shopping.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ReviewDTO {

    //user
    private int reviewId;
    private String userId;
    private String userEmail;
    private  String userName;
    private  int userAge;

    //product
    private  String userNickName;
    private  String ProductName;
    private  int ProductId;
    private  long ProductPrice;

    //review
    private  String imgUrl;
    private  String title;
    private  String content;


    @Builder
    public ReviewDTO(String userId, String userEmail, String userName, int userAge, String userNickName, String productName, int productId, long productPrice, String imgUrl, String title, String content, int reviewId) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAge = userAge;
        this.userNickName = userNickName;
        ProductName = productName;
        ProductId = productId;
        ProductPrice = productPrice;
        this.imgUrl = imgUrl;
        this.title = title;
        this.content = content;
    }
}
