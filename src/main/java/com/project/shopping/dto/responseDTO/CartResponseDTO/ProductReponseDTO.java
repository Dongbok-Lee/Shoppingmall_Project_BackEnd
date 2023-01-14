package com.project.shopping.dto.responseDTO.CartResponseDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductReponseDTO {
    private  int productId;
    private  String title;
    private  String name;
    private  String content;
    private String imgUrl;
    private  long price;

    @Builder
    public ProductReponseDTO(int productId, String title, String name, String content, String imgUrl, long price) {
        this.productId = productId;
        this.title = title;
        this.name = name;
        this.content = content;
        this.imgUrl = imgUrl;
        this.price = price;
    }
}
