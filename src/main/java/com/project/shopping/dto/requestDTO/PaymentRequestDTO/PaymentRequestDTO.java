package com.project.shopping.dto.requestDTO.PaymentRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@RequiredArgsConstructor
@Getter
public class PaymentRequestDTO {

    @NotBlank
    private String impUid;

    @NotBlank
    private String orderId;

    @Builder
    public PaymentRequestDTO(String impUid, String orderId) {
        this.impUid = impUid;
        this.orderId = orderId;
    }
}
