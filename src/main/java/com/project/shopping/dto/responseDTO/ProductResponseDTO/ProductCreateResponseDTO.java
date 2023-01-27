package com.project.shopping.dto.responseDTO.ProductResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class ProductCreateResponseDTO {

    private String title;
    private  String content;
    private  String name;
    private  long price;
    private  int total;
    private String imgUrl;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @Builder

    public ProductCreateResponseDTO( String title, String content, String name, long price, int total, String imgUrl, LocalDateTime createDate, LocalDateTime modifiedDate) {

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
