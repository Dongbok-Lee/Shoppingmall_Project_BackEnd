package com.project.shopping.dto.responseDTO.PaymentResponseDTO;

import com.project.shopping.dto.responseDTO.OrderResponseDTO.ProductInOrderResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
public class PaymentCompleteResponseDTO {
    private String orderId;
    @Setter
    private  String orderComplete;
    private  ArrayList<ProductInOrderResponseDTO> products;
    private  Long amount;
    @Setter
    private LocalDateTime paymentCompleteDate;

    @Builder
    public PaymentCompleteResponseDTO(String orderId, String orderComplete, ArrayList<ProductInOrderResponseDTO> products, Long amount, LocalDateTime paymentCompleteDate) {
        this.orderId = orderId;
        this.orderComplete = orderComplete;
        this.products = products;
        this.amount = amount;
        this.paymentCompleteDate = paymentCompleteDate;
    }
}