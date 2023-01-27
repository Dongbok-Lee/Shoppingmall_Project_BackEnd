package com.project.shopping.dto.requestDTO.CartRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class CartCreateRequestDTO {

    @NotNull
    private  long productNum;

    @Builder
    public CartCreateRequestDTO(long productNum) {
        this.productNum = productNum;
    }

}
