package com.project.shopping.dto.responseDTO.CartResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
public class CartUpdateResosneDTO {
    private  int cartId;
    private ProductReponseDTO product;
    private  long productNum;
    private LocalDateTime createDate;


    @Builder
    public CartUpdateResosneDTO(int cartId, ProductReponseDTO product, long productNum, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.cartId = cartId;
        this.product = product;
        this.productNum = productNum;
        this.createDate = createDate;

    }
}
