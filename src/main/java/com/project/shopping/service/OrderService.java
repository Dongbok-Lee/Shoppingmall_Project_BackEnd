package com.project.shopping.service;

import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.dto.requestDTO.OrderRequestDTO.OrderRequestDTO;
import com.project.shopping.dto.responseDTO.OrderResponseDTO.OrderResponseDTO;
import com.project.shopping.dto.responseDTO.OrderResponseDTO.ProductInOrderResponseDTO;
import com.project.shopping.model.*;
import com.project.shopping.repository.OrderRepository;
import com.project.shopping.repository.ProductRepository;
import com.project.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderResponseDTO create(String email, OrderRequestDTO orderRequestDTO)throws CustomException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new CustomException("User Not Found.", ErrorCode.NotFoundUserException));
        ArrayList<OrderDetail> products = new ArrayList<>();
        ArrayList<ProductInOrderResponseDTO>  responseProductsDTO = new ArrayList<>();
        long totalAmount = 0;
        Order order = Order.builder()
                .products(products)
                .user(user)
                .orderComplete("ready")
                .status("active")
                .build();

        for(int i = 0; i < orderRequestDTO.getProductsId().size(); i++){
            Product product = productRepository.findById((int)orderRequestDTO.getProductsId().get(i))
                    .orElseThrow(()-> new CustomException("Product Not Found", ErrorCode.NotFoundProductException));
            int productNum = orderRequestDTO.getProductsNumber().get(i);

            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .productNum(productNum)
                    .build();

            ProductInOrderResponseDTO productDTO = ProductInOrderResponseDTO.builder()
                    .productId(product.getId())
                    .productName(product.getTitle())
                    .imgUrl(product.getImgUrl())
                    .productNum(productNum)
                    .build();

            order.addProduct(orderDetail);
            responseProductsDTO.add(productDTO);
            totalAmount += product.getPrice() * productNum;
        }

        order.setAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        OrderResponseDTO orderResponseDTO = OrderResponseDTO.builder()
                .orderId(savedOrder.getId())
                .products(responseProductsDTO)
                .orderComplete(savedOrder.getOrderComplete())
                .amount(savedOrder.getAmount())
                .orderTime(savedOrder.getOrderTime())
                .build();

        if(order.getProducts() == null){
            log.warn("product not found", order.getProducts());
            throw new RuntimeException("product not found");
        }

        return orderResponseDTO;
    }

    public Order update(Order order){
        if(order.getProducts()== null){
            throw new NoSuchElementException("해당 상품이 없습니다.");
        }
        return orderRepository.save(order);
    }

/*    public Order findById(int id){
        Order order = orderRepository.findById(id)
            .orElseThrow(()->new
                    IllegalArgumentException("order Not Found."));
        return order;
    }*/

    public List<OrderResponseDTO> getOrderList(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("",ErrorCode.NotFoundUserException));
        List<Order> orderList = orderRepository.findAllByUser(user);
        List<OrderResponseDTO> orders = new ArrayList<>();

        for(Order order : orderList){
            ArrayList<ProductInOrderResponseDTO> products = new ArrayList<>();

            for(int i = 0; i < order.getProducts().size(); i++){
                ProductInOrderResponseDTO productDTO = new ProductInOrderResponseDTO();
                Product product = order.getProducts().get(i).getProduct();
                int productNum = order.getProducts().get(i).getProductNum();

                productDTO = ProductInOrderResponseDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .productNum(productNum)
                        .imgUrl(product.getImgUrl())
                        .build();

                products.add(productDTO);
            }

            OrderResponseDTO orderDTO = OrderResponseDTO.builder()
                    .orderId(order.getId())
                    .products(products)
                    .orderComplete(order.getOrderComplete())
                    .amount(order.getAmount())
                    .orderTime(order.getOrderTime())
                    .build();

            orders.add(orderDTO);
        }

        return orders;
    }
}
