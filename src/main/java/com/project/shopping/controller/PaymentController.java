package com.project.shopping.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.dto.requestDTO.PaymentRequestDTO.PaymentRequestDTO;
import com.project.shopping.dto.responseDTO.OrderResponseDTO.ProductInOrderResponseDTO;
import com.project.shopping.dto.responseDTO.PaymentResponseDTO.PaymentCompleteResponseDTO;
import com.project.shopping.model.Order;
import com.project.shopping.model.Product;
import com.project.shopping.service.OrderService;
import com.project.shopping.service.PaymentService;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.project.shopping.model.QProduct.product;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;


    @PostMapping("/api/payments/complete")
    public ResponseEntity<?> verifyPayment(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) throws Exception{
        PaymentCompleteResponseDTO paymentCompleteResponseDTO = paymentService.verifyPayment(paymentRequestDTO);

        Map<String, Object> result = new HashMap<String, Object>();

        if(paymentCompleteResponseDTO.getOrderComplete().equals("complete"))
            result.put("msg", "결제가 완료되었습니다.");
        else
            result.put("msg", "가상계좌 서비스는 준비중입니다.");

        result.put("data", paymentCompleteResponseDTO);
        return ResponseEntity.ok().body(result);
    }
}

