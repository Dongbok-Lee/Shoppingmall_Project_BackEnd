package com.project.shopping.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderDTO {
    private int productId;
    private int productNumber;

    public OrderDTO(int productId, int productNumber) {
        this.productId = productId;
        this.productNumber = productNumber;
    }
}
