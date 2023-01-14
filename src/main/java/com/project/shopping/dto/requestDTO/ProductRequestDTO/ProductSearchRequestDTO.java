package com.project.shopping.dto.requestDTO.ProductRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class ProductSearchRequestDTO {

    @NotBlank
    private  String keyword;

    @Builder

    public ProductSearchRequestDTO(String keyword) {
        this.keyword = keyword;
    }
}
