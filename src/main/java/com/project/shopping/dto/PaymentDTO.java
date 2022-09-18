package com.project.shopping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PaymentDTO {
    private String impUid;
    private int productId;

    @Builder
    public PaymentDTO(String impUid, int productId) {
        this.impUid = impUid;
        this.productId = productId;
    }
}
