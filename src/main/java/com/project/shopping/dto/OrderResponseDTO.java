package com.project.shopping.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderResponseDTO {
    private int merchantUid;
    private String name;
    private Long amount;
    private String buyerEmail;
    private String buyerName;
    private String buyerTel;
    private String buyerAddr;
    private String buyerPostcode;
    private boolean status;

    @Builder
    public OrderResponseDTO(int merchantUid, String name, Long amount, String buyerEmail, String buyerName, String buyerTel, String buyerAddr, String buyerPostcode, boolean status) {
        this.merchantUid = merchantUid;
        this.name = name;
        this.amount = amount;
        this.buyerEmail = buyerEmail;
        this.buyerName = buyerName;
        this.buyerTel = buyerTel;
        this.buyerAddr = buyerAddr;
        this.buyerPostcode = buyerPostcode;
        this.status = status;
    }
}
