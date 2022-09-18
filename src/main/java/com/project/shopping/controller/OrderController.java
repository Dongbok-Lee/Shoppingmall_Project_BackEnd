package com.project.shopping.controller;

import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.OrderDTO;
import com.project.shopping.dto.OrderResponseDTO;
import com.project.shopping.dto.ProductDTO;
import com.project.shopping.model.Order;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.service.OrderService;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/order")
    public ResponseEntity<?> getOrderList(Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User user = principalDetails.getUser();

        List<Order> orderList = orderService.findAllByUserId(user);
        ArrayList<OrderResponseDTO> orders = new ArrayList<>();

        for(Order order : orderList){
            orders.add(
                    OrderResponseDTO.builder()
                            .merchantUid(order.getProductId().getId())
                            .name(order.getProductId().getName())
                            .amount(order.getAmount())
                            .buyerEmail(order.getUserId().getEmail())
                            .buyerName(order.getUserId().getUsername())
                            .buyerTel(order.getUserId().getPhoneNumber())
                            .buyerAddr(order.getUserId().getAddress())
                            .buyerPostcode(order.getUserId().getPostCode())
                            .build()
            );
        }
        Map<String , Object> result = new HashMap<>();
        result.put("data", orders);

        return ResponseEntity.ok().body(result);
    }


    @PostMapping("/order/create")
    public ResponseEntity<?> createOrder(Authentication authentication, @RequestBody OrderDTO orderDTO){
        try{
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email);
            Product product = productService.findproductid(orderDTO.getProductId());

            Order order = Order.builder()
                    .productId(product)
                    .userId(user)
                    .productNum(orderDTO.getProductNumber())
                    .orderComplete(false)
                    .amount(product.getPrice() * orderDTO.getProductNumber())
                    .build();

            orderService.create(order);

            OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                    .merchantUid(orderDTO.getProductId())
                    .name(product.getName())
                    .amount(orderDTO.getProductNumber() * product.getPrice())
                    .buyerEmail(user.getEmail())
                    .buyerTel(user.getPhoneNumber())
                    .buyerAddr(user.getAddress())
                    .buyerPostcode(user.getPostCode())
                    .status(false)
                    .build();

            return ResponseEntity.ok().body(orderResponseDTO);
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
