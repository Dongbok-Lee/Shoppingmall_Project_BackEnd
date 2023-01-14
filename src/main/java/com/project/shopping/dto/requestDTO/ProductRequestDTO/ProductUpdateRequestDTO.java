package com.project.shopping.dto.requestDTO.ProductRequestDTO;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class ProductUpdateRequestDTO {
    @NotBlank
    private String title;
    @NotBlank
    private  String content;
    @NotBlank
    private  String name;
    @NotNull
    private  long price;

    @NotNull
    private  int total;
    @NotBlank
    private String imgUrl;

    @Builder

    public ProductUpdateRequestDTO(String title, String content, String name, long price, int total, String imgUrl) {
        this.title = title;
        this.content = content;
        this.name = name;
        this.price = price;
        this.total = total;
        this.imgUrl = imgUrl;
    }
}
