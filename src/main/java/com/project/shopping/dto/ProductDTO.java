package com.project.shopping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class ProductDTO {
    private  int productId;

    private String userId;
    private String userEmail;
    private String userPhoneNumber;
    private String userName;
    private String title;
    private  String content;
    private  String name;
    private  long price;
    private  int total;
    private String imgUrl;
    private Timestamp createDate;


    @Builder
    public ProductDTO(int productId, String useremail, String userId, String userPhoneNumber, String userName, String title, String content, String name, long price, int total, String imgUrl, Timestamp createDate) {
        this.productId = productId;
        this.userEmail = useremail;
        this.userId = userId;
        this.userPhoneNumber = userPhoneNumber;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.name = name;
        this.price = price;
        this.total = total;
        this.imgUrl = imgUrl;
        this.createDate = createDate;
    }
}
