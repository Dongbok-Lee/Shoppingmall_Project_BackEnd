package com.project.shopping.controller;

import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.*;
import com.project.shopping.model.Cart;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.service.CartService;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    private  CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // 상품 생성
    // product id를 받아서 해당 상품을 찾고 인증객체를 통한 user 을 받아 장바구니 생성
    @PostMapping("/cart/create/{id}")
    // 여기서 ID는 상품 ID
    public ResponseEntity<?> createCart(Authentication authentication, @PathVariable(value = "id") int ProductId ,@RequestBody CartDTO cartDTO){
        try{
            PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = userDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email); // user 찾기
            Product product = productService.findproductid(ProductId); // 상품 찾기

            if(cartDTO.getCarttotal() > product.getTotal()){
                throw  new Exception("상품 개수 초과 ");
            }

            // 유저가 장바구니에 담은 상품이 기존에 있을시
            if(cartService.existsCartByUserIdAndProductId(user, product)){ // 해당 상품이 존재 할시
                Cart findCart = cartService.findCartByUserIdAndProductId(user, product); // 캍찾기
                long totalsum = findCart.getCarttotal()+ cartDTO.getCarttotal();
                //System.out.println(totalsum);
                if(totalsum> product.getTotal()){
                    throw  new Exception("상품 개수 초과 ");
                }
                findCart.setCarttotal(totalsum);
                Cart createcart = cartService.create(findCart);

                CartDTO response = CartDTO.builder().cartId(createcart.getId()).build();
                return ResponseEntity.ok().body(response);

            }else{
                Cart cart = Cart.builder().productId(product).userId(user).createTime(Timestamp.valueOf(LocalDateTime.now())).carttotal(cartDTO.getCarttotal()).build();
                Cart createcart = cartService.create(cart); // 카트 생성
                CartDTO response = CartDTO.builder().cartId(createcart.getId()).build();



                return ResponseEntity.ok().body(response);
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
    // 장바구니 삭제


    // 장바구니 해당 아이디

    @GetMapping("/cart/list")
    public ResponseEntity<?> cartlist(Authentication authentication){
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String userEmail = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(userEmail);
            List<Cart> cartList = cartService.findallByUserId(user);
            List<CartDTO> cartdtos = new ArrayList<>();
            long totalsum = 0; // 최종 합계
            int totalcarttotal =0;
            for(Cart cart: cartList){
                CartDTO cartDto = CartDTO.builder()
                        .cartId(cart.getId())
                        .userId(cart.getUserId().getUserId())
                        .userEmail(cart.getUserId().getEmail())
                        .userNickName(cart.getUserId().getNickname())
                        .userName(cart.getUserId().getUsername())
                        .userAddress(cart.getUserId().getAddress())
                        .userAge(cart.getUserId().getAge())
                        .userPhoneNumber(cart.getUserId().getPhoneNumber())
                        .productName(cart.getProductId().getName())
                        .productId(cart.getProductId().getId())
                        .productPrice(cart.getProductId().getPrice())
                        .productTotal(cart.getProductId().getTotal())
                        .imgUrl(cart.getProductId().getImgUrl())
                        .carttotal(cart.getCarttotal())
                        .createTime(cart.getCreateTime()).build();

                cartdtos.add(cartDto);
                totalsum  = (totalsum + (cart.getCarttotal() * cart.getProductId().getPrice()));
                totalcarttotal = (int) (totalcarttotal +cart.getCarttotal());

            }
            Map<String, Object> result = new HashMap<>();
            result.put("data", cartdtos);
            result.put("totalsum", totalsum);
            result.put("totalcarttotal", totalcarttotal);
            // list.size

            //System.out.println(totalsum);


            return  ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    // 삭제 쿼리
    @DeleteMapping("/cart/delete/{id}") // id는 cart_Id
    public ResponseEntity<?> cartdelete(Authentication authentication, @PathVariable(value = "id")int id){
        try {
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();

            User user = userService.findEmailByUser(email);
            Cart findcart = cartService.findCartUserAndId(user, id);
            cartService.deleteCart(findcart);

            CartDTO cartDTO = CartDTO.builder()
                    .userEmail(user.getEmail())
                    .productName(findcart.getProductId().getName())
                    .build();
            return  ResponseEntity.ok().body(cartDTO);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cart/update/{id}")
    public ResponseEntity<?> cartupdate(Authentication authentication, @PathVariable(value = "id") int cartId , @RequestBody CartCountDTO cartCountDTO){
        try{

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email); // 유저 찾기

            Cart cart = cartService.findCartUserAndId(user,cartId); // 유저랑 카트 아이디를 이용한 카트 찾기
            long totalcount =  (cartCountDTO.getCarttotal());

            //System.out.println(totalcount);
            if(totalcount > cart.getProductId().getTotal()){
                throw  new Exception("상품 개수 초과 ");
            }
            if(totalcount <1){
                throw new Exception("마이너스.");
            }

            cart.setCarttotal(cartCountDTO.getCarttotal());
            Cart updatecart = cartService.create(cart);
            System.out.println(cartCountDTO.getCarttotal() + "cartcount");
            System.out.println(totalcount+ "totalcount");
            System.out.println(updatecart.getCarttotal() + "update");

            CartDTO response  = CartDTO.builder()
                    .cartId(updatecart.getId())
                    .userId(updatecart.getUserId().getUserId())
                    .userEmail(updatecart.getUserId().getEmail())
                    .userNickName(updatecart.getUserId().getNickname())
                    .userName(updatecart.getUserId().getUsername())
                    .userAddress(updatecart.getUserId().getAddress())
                    .userAge(updatecart.getUserId().getAge())
                    .userPhoneNumber(updatecart.getUserId().getPhoneNumber())
                    .productName(updatecart.getProductId().getName())
                    .productId(updatecart.getProductId().getId())
                    .productPrice(updatecart.getProductId().getPrice())
                    .productTotal(updatecart.getProductId().getTotal())
                    .imgUrl(updatecart.getProductId().getImgUrl())
                    .carttotal(updatecart.getCarttotal())
                    .createTime(updatecart.getCreateTime()).build();

            return  ResponseEntity.ok().body(response);


        }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());

        }



    }





}
