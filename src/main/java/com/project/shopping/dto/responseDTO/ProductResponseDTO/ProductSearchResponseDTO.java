package com.project.shopping.dto.responseDTO.ProductResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ProductSearchResponseDTO {
    private  int productId;
    private String title;
    private  String content;
    private  String name;
    private  long price;
    private  int total;
    private String imgUrl;
    private Timestamp createDate;
    private Timestamp modifiedDate;

    @Builder

    public ProductSearchResponseDTO(int productId, String title, String content, String name, long price, int total, String imgUrl, Timestamp createDate, Timestamp modifiedDate) {
        this.productId = productId;
        this.title = title;
        this.content = content;
        this.name = name;
        this.price = price;
        this.total = total;
        this.imgUrl = imgUrl;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
