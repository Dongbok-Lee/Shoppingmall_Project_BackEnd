package com.project.shopping.dto.requestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@NoArgsConstructor
@Getter
public class request {
    private  String info;

    @Builder

    public request(String info) {
        this.info = info;
    }
}
