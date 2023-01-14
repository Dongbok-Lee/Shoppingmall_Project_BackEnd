package com.project.shopping.dto.responseDTO.CartResponseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
public class CartUpdateResosneDTO {
    private  int cartId;
    private ProductReponseDTO product;
    private  long productNum;
    private Timestamp createDate;


    @Builder
    public CartUpdateResosneDTO(int cartId, ProductReponseDTO product, long productNum, Timestamp createDate, Timestamp modifiedDate) {
        this.cartId = cartId;
        this.product = product;
        this.productNum = productNum;
        this.createDate = createDate;

    }
}
