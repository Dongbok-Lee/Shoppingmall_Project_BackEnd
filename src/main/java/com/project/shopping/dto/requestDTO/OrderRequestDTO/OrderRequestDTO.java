package com.project.shopping.dto.requestDTO.OrderRequestDTO;

import com.project.shopping.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@NoArgsConstructor
@Getter
public class OrderRequestDTO {

    @NotNull
    @Size(min = 1)
    private ArrayList<Integer> productsId;

    @NotNull
    @Size(min = 1)
    private ArrayList<Integer> productsNumber;


    public OrderRequestDTO(ArrayList<Integer> productsId, ArrayList<Integer> productsNumber) {
        this.productsId = productsId;
        this.productsNumber = productsNumber;
    }
}



