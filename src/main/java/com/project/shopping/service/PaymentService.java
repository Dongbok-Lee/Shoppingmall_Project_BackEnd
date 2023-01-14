package com.project.shopping.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.dto.requestDTO.PaymentRequestDTO.PaymentRequestDTO;
import com.project.shopping.dto.responseDTO.OrderResponseDTO.ProductInOrderResponseDTO;
import com.project.shopping.dto.responseDTO.PaymentResponseDTO.PaymentCompleteResponseDTO;
import com.project.shopping.model.Order;
import com.project.shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ProductService productService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Value("${imp-key}")
    private String impKey;

    @Value("${imp-secret}")
    private String impSecret;

    public PaymentCompleteResponseDTO verifyPayment(PaymentRequestDTO paymentRequestDTO) {

        final String tokenUrl = "https://api.iamport.kr/users/getToken";
        final String paymentInfoUrl = "https://api.iamport.kr/payments/" + paymentRequestDTO.getImpUid();

        System.out.println(paymentRequestDTO.getImpUid());
        try {
            //getAccessToken from i'mport Server
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> body = new HashMap<>();
            body.put("imp_key", impKey);
            body.put("imp_secret", impSecret);
            HttpEntity<Map<String, String>> requestMessage = new HttpEntity<>(body, httpHeaders);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, requestMessage, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String token = root.path("response").path("access_token").asText();
            //getPaymentInformation from i'mport sever
            httpHeaders.setBearerAuth(token);
            requestMessage = new HttpEntity(httpHeaders);
            response = restTemplate.exchange(
                    paymentInfoUrl,
                    HttpMethod.GET,
                    requestMessage,
                    String.class
            );
            //가격비교하고, order에 결제상태 저장
            root = mapper.readTree(response.getBody());
            String orderId = root.path("response").path("merchant_uid").asText();
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(()->new
                            CustomException("order Not Found.",ErrorCode.NotFoundOrderException));
            ArrayList<ProductInOrderResponseDTO> productInOrderResponseDTOList = new ArrayList<ProductInOrderResponseDTO>();
            ProductInOrderResponseDTO productInOrderResponseDTO;
            for (int i = 0; i < order.getProducts().size(); i++) {
                productInOrderResponseDTO = ProductInOrderResponseDTO.builder()
                        .productId(order.getProducts().get(i).getProduct().getId())
                        .productName(order.getProducts().get(i).getProduct().getName())
                        .productNum(order.getProducts().get(i).getProductNum())
                        .build();

                productInOrderResponseDTOList.add(productInOrderResponseDTO);
            }

             PaymentCompleteResponseDTO paymentCompleteResponseDTO = PaymentCompleteResponseDTO.builder()
                    .orderId(order.getId())
                    .products(productInOrderResponseDTOList)
                    .amount(order.getAmount())
                    .build();

            if (order.getAmount() == Integer.parseInt(root.path("response").path("amount").asText())) {
                String status = root.path("response").path("status").asText();

                switch (status) {
                    case "ready":
                        order.setOrderComplete("failed");

                    case "paid":
                        order.setPaymentCompleteDate(LocalDateTime.now());
                        order.setOrderComplete("complete");
                }
                Order updatedOrder = orderService.update(order);
                paymentCompleteResponseDTO.setOrderComplete(updatedOrder.getOrderComplete());

                paymentCompleteResponseDTO.setOrderComplete(order.getOrderComplete());
                paymentCompleteResponseDTO.setPaymentCompleteDate(order.getPaymentCompleteDate());

                return paymentCompleteResponseDTO;

            } else throw new CustomException("위조된 결제 시도 입니다.", ErrorCode.BadParameterException);

        } catch (Exception e) {
            //convert error code unicode to korean
            StringBuffer str = new StringBuffer();
            String uni = e.getMessage();
            for (int i = uni.indexOf("\\u"); i > -1; i = uni.indexOf("\\u")) {
                str.append(uni.substring(0, i));
                str.append(String.valueOf((char) Integer.parseInt(uni.substring(i + 2, i + 6), 16)));
                uni = uni.substring(i + 6);
            }
            str.append(uni);
            String msg = str.toString();
            throw new CustomException(msg, ErrorCode.INTER_SERVER_ERROR);
        }
    }
}
