package com.project.shopping.dto.responseDTO.CartResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CartCreateResponseDTO {
    private  int cartId;
    private ProductReponseDTO product;
    private  long productNum;
    private LocalDateTime createDate;


    @Builder
    public CartCreateResponseDTO(int cartId, ProductReponseDTO product, long productNum, LocalDateTime createDate) {
        this.cartId = cartId;
        this.product = product;
        this.productNum = productNum;
        this.createDate = createDate;

    }
}
