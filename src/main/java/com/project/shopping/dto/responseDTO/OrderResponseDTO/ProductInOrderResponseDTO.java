package com.project.shopping.dto.responseDTO.OrderResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductInOrderResponseDTO {

    private int productId;
    private String productName;
    private String imgUrl;
    private int productNum;

    @Builder
    public ProductInOrderResponseDTO(int productId, String productName, String imgUrl, int productNum) {
        this.productId = productId;
        this.productName = productName;
        this.imgUrl = imgUrl;
        this.productNum = productNum;
    }
}
