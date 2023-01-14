package com.project.shopping.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@NoArgsConstructor
@Getter
public class CartDTO {

    private  int cartId;
   private String userId;
   private  String userEmail;
   private String userPhoneNumber;
   private  String userName;
   private  String userAddress;
   private  int userAge;
   private  String userNickName;

   //상품
    private  String ProductName;
    private  int ProductId;
    private  long ProductPrice;
    private  int ProductTotal;
    private  String imgUrl;
    private Timestamp createTime;
    private long carttotal;

    @Builder

    public CartDTO(int cartId,String userId, String userEmail,String userNickName ,String userPhoneNumber, String userName, String userAddress, int userAge, String productName, int productId, long productPrice, int productTotal, String imgUrl, Timestamp createTime,long carttotal) {
        this.cartId  = cartId;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.userNickName = userNickName;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userAge = userAge;
        ProductName = productName;
        ProductId = productId;
        ProductPrice = productPrice;
        ProductTotal = productTotal;
        this.imgUrl = imgUrl;
        this.createTime = createTime;
        this.carttotal = carttotal;
    }
}
