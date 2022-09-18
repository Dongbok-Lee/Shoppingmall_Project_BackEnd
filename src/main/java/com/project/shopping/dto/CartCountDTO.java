package com.project.shopping.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartCountDTO {
    private long carttotal;


    @Builder
    public CartCountDTO(int carttotal) {
        this.carttotal = carttotal;
    }
}
