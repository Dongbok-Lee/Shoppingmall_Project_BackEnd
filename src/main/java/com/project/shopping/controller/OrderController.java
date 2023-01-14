package com.project.shopping.controller;

import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.requestDTO.OrderRequestDTO.OrderRequestDTO;
import com.project.shopping.dto.responseDTO.OrderResponseDTO.OrderResponseDTO;
import com.project.shopping.dto.responseDTO.OrderResponseDTO.ProductInOrderResponseDTO;
import com.project.shopping.model.Order;
import com.project.shopping.model.OrderDetail;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.service.OrderDetailService;
import com.project.shopping.service.OrderService;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/api/order")
    public ResponseEntity<?> getOrderList(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();

        List<OrderResponseDTO> orders = orderService.getOrderList(email);

        Map<String , Object> result = new HashMap<>();
        result.put("data", orders);

        return ResponseEntity.ok().body(result);
    }


    @PostMapping("/api/order/create")
    public ResponseEntity<?> createOrder(Authentication authentication,@Valid @RequestBody OrderRequestDTO orderRequestDTO){
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();

            OrderResponseDTO orderResponseDTO = orderService.create(email, orderRequestDTO);
            Map<String, Object> result = new HashMap<>();
            result.put("msg", "주문 등록에 성공했습니다.");
            result.put("data", orderResponseDTO);

            return ResponseEntity.ok().body(result);
    }
}
