package com.project.shopping.controller;

import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.*;
import com.project.shopping.dto.requestDTO.CartRequestDTO.CartCreateRequestDTO;
import com.project.shopping.dto.requestDTO.CartRequestDTO.CartUpdateRequestDTO;
import com.project.shopping.dto.responseDTO.CartResponseDTO.*;
import com.project.shopping.model.Cart;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.service.CartService;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {

    private  final CartService cartService;


    private final  ProductService productService;


    private final UserService userService;

    private  String ActiveStatus = "active";

    // 상품 생성
    // product id를 받아서 해당 상품을 찾고 인증객체를 통한 user 을 받아 장바구니 생성
    @PostMapping("/api/cart/create/{id}")
    // 여기서 ID는 상품 ID
    public ResponseEntity<?> createCart(Authentication authentication, @PathVariable(value = "id") int ProductId ,@RequestBody @Valid CartCreateRequestDTO cartCreateRequestDTO){
        if(authentication == null){
            throw  new CustomException("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }
        CartCreateResponseDTO createcart = cartService.create(authentication, ProductId, cartCreateRequestDTO); // 카트 생성
        Map<String, Object> result = new HashMap<>();
        result.put("msg", "장바구니 등록에 성공했습니다.");
        result.put("data", createcart);

        // 유저가 장바구니에 담은 상품이 기존에 있을시
        return  ResponseEntity.ok().body(result);


    }
    // 장바구니 삭제


    // 장바구니 해당 아이디


    @GetMapping("/api/cart/user")
    public ResponseEntity<?> cartlist(Authentication authentication){
        if(authentication == null){
            throw  new CustomException("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        List<CartUserListJoinResponseDTO> cartList = cartService.getEqUserAndCart(authentication,ActiveStatus);

        Map<String, Object> result = new HashMap<>();
        result.put("msg", " 장바구니 조회에 성공했습니다.");
        result.put("data",cartList);
        // list.size

        //System.out.println(totalsum);


        return  ResponseEntity.ok().body(result);


    }

    // 삭제 쿼리
    @DeleteMapping("/api/cart/delete/{id}") // id는 cart_Id
    public ResponseEntity<?> cartdelete(Authentication authentication, @PathVariable(value = "id")int id){
        if(authentication == null){
            throw  new CustomException("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        CartDeleteResponseDTO cartDeleteResponseDTO = cartService.deleteCart(authentication, id);


        Map<String, Object> result = new HashMap<>();
        result.put("msg","장바구니 삭제에 성공했습니다.");
        result.put("data", cartDeleteResponseDTO);
        return  ResponseEntity.ok().body(result);
    }

    @PutMapping("/api/cart/update/{id}")
    public ResponseEntity<?> cartupdate(Authentication authentication, @PathVariable(value = "id") int cartId , @RequestBody @Valid CartUpdateRequestDTO cartUpdateRequestDTO){

        if(authentication == null){
            throw  new CustomException("허용되지 않은 접근입니다." , ErrorCode.UnauthorizedException);
        }

        CartUpdateResosneDTO updatecart = cartService.update(authentication, cartId, cartUpdateRequestDTO);
//            System.out.println(cartUpdateRequestDTO.getProductNum() + "cartcount"); // 1
//            System.out.println(totalcount+ "totalcount");
//            System.out.println(updatecart.getProductNum() + "update");


        Map<String, Object> result = new HashMap<>();
        result.put("msg","장바구니 수정에 성공했습니다.");
        result.put("data", updatecart);

        return  ResponseEntity.ok().body(result);



    }





}
