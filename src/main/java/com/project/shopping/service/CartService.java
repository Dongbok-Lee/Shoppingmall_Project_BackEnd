package com.project.shopping.service;

import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.requestDTO.CartRequestDTO.CartCreateRequestDTO;
import com.project.shopping.dto.requestDTO.CartRequestDTO.CartUpdateRequestDTO;
import com.project.shopping.dto.responseDTO.CartResponseDTO.*;
import com.project.shopping.model.Cart;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.repository.CartRepository;
import com.project.shopping.repository.ProductRepository;
import com.project.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {

    private  final CartRepository cartRepository;
    private  final ProductRepository productRepository;
    private  final UserRepository userRepository;

    //생성
    public  CartCreateResponseDTO create(Authentication authentication, int ProductId, CartCreateRequestDTO cartCreateRequestDTO){
        PrincipalDetails userDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = userDetails.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("User Not Found",ErrorCode.NotFoundUserException));// user 찾기
        Product product = productRepository.findById(ProductId)
                .orElseThrow(()-> new CustomException("Product Not Found", ErrorCode.NotFoundProductException));// 상품 찾기


        if(cartRepository.existsCartByUserIdAndProductIdAndStatus(user, product,"active")){ // 해당 상품이 존재 할시
            Cart findCart = cartRepository.findCartByUserIdAndProductId(user, product)
                    .orElseThrow(()-> new CustomException("Cart Not Found", ErrorCode.NotFoundCartException));// 카트찾기
            long totalsum = findCart.getProductNum()+ cartCreateRequestDTO.getProductNum();
            //System.out.println(totalsum);
            if(totalsum> product.getTotal()){
                throw  new CustomException("상품개수가 초과하였습니다.",ErrorCode.NotFoundCartNumException);
            }
            findCart.setProductNum(totalsum);
            Cart createcart = cartRepository.save(findCart); // 카트 생성
            ProductReponseDTO productReponseDTO = ProductReponseDTO.builder()
                    .productId(createcart.getProductId().getId())
                    .title(createcart.getProductId().getTitle())
                    .name(createcart.getProductId().getName())
                    .content(createcart.getProductId().getContent())
                    .imgUrl(createcart.getProductId().getImgUrl())
                    .price(createcart.getProductId().getPrice())
                    .build();

            CartCreateResponseDTO cartCreateResponseDTO = CartCreateResponseDTO
                    .builder()
                    .cartId(createcart.getId())
                    .product(productReponseDTO)
                    .productNum(createcart.getProductNum())
                    .createDate(createcart.getCreateTime())
                    .build();

            return cartCreateResponseDTO;

        }else{
            Cart cart = Cart.builder()
                    .productId(product)
                    .userId(user)
                    .createTime(Timestamp.valueOf(LocalDateTime.now()))
                    .productNum(cartCreateRequestDTO.getProductNum())
                    .status("active")
                    .build();
            Cart createcart = cartRepository.save(cart); // 카트 생성
            ProductReponseDTO productReponseDTO = ProductReponseDTO.builder()
                    .productId(createcart.getProductId().getId())
                    .title(createcart.getProductId().getTitle())
                    .name(createcart.getProductId().getName())
                    .content(createcart.getProductId().getContent())
                    .imgUrl(createcart.getProductId().getImgUrl())
                    .price(createcart.getProductId().getPrice())
                    .build();
            CartCreateResponseDTO cartCreateResponseDTO = CartCreateResponseDTO
                    .builder()
                    .cartId(createcart.getId())
                    .product(productReponseDTO)
                    .productNum(createcart.getProductNum())
                    .createDate(createcart.getCreateTime())
                    .build();


            return cartCreateResponseDTO;
        }

    }

    public CartUpdateResosneDTO update(Authentication authentication, int cartId, CartUpdateRequestDTO cartUpdateRequestDTO){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("",ErrorCode.NotFoundUserException));// 유저 찾기

        Cart cart = cartRepository.findCartByUserIdAndId(user,cartId)
                .orElseThrow(()-> new CustomException("Cart Not Found", ErrorCode.NotFoundCartException));// 유저랑 카트 아이디를 이용한 카트 찾기
        long totalcount =  (cartUpdateRequestDTO.getProductNum());

        //System.out.println(totalcount);
        if(totalcount > cart.getProductId().getTotal()){
            throw new CustomException("상품의 구매한도를 초과했습니다.", ErrorCode.NotFoundCartNumException);
        }
        if(totalcount <1){
            throw new CustomException("상품의 구매한도를 초과했습니다.", ErrorCode.NotFoundCartNumDownException);
        }

        cart.setProductNum(cartUpdateRequestDTO.getProductNum());

        cartRepository.save(cart);


        ProductReponseDTO productReponseDTO = ProductReponseDTO.builder()
                .productId(cart.getProductId().getId())
                .title(cart.getProductId().getTitle())
                .name(cart.getProductId().getName())
                .content(cart.getProductId().getContent())
                .imgUrl(cart.getProductId().getImgUrl())
                .price(cart.getProductId().getPrice())
                .build();

        CartUpdateResosneDTO cartUpdateResosneDTO  = CartUpdateResosneDTO.builder()
                .cartId(cart.getId())
                .product(productReponseDTO)
                .productNum(cart.getProductNum())
                .createDate(cart.getCreateTime())
                .build();
        return cartUpdateResosneDTO;
    }

    // 삭제
    public  CartDeleteResponseDTO deleteCart(Authentication authentication, int id){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("",ErrorCode.NotFoundUserException));
//        if(!cartRepository.existsByUserIdAndId(user, id)){
//            throw  new CustomException("Cart Not Found",ErrorCode.NotFoundCartException);
//        } // 밑에 코드랑 같은 코드 임

        Cart findcart = cartRepository.findCartByUserIdAndId(user, id)
                        .orElseThrow(()-> new CustomException("Cart Not Found", ErrorCode.NotFoundCartException));
        findcart.setStatus("Disabled");

        cartRepository.save(findcart);
        ProductReponseDTO productReponseDTO = ProductReponseDTO.builder()
                .productId(findcart.getProductId().getId())
                .title(findcart.getProductId().getTitle())
                .name(findcart.getProductId().getName())
                .content(findcart.getProductId().getContent())
                .imgUrl(findcart.getProductId().getImgUrl())
                .price(findcart.getProductId().getPrice())
                .build();

        CartDeleteResponseDTO cartDeleteResponseDTO = CartDeleteResponseDTO.builder()
                .cartId(findcart.getId())
                .product(productReponseDTO)
                .productNum(findcart.getProductNum())
                .createDate(findcart.getCreateTime())
                .build();



        return cartDeleteResponseDTO;


    }
    // user가 등록한 장바구니 목록 조회
    public  List<Cart> findallByUserId(User user){return cartRepository.findAllByuserId(user); }

    // 유저  와 cartid를 이용한 해당 장바구니 상품 조회
    public Cart findCartUserAndId(User user, int id){
        return cartRepository.findCartByUserIdAndId(user, id)
                .orElseThrow(()-> new CustomException("Cart Not Found", ErrorCode.NotFoundCartException));
    }

    public  boolean existCartUserAndId(User user, int id){return  cartRepository.existsByUserIdAndId(user, id);}


    public boolean existsCartByUserIdAndProductId(User user, Product product){return cartRepository.existsCartByUserIdAndProductIdAndStatus(user,product,"active");}

    public Cart findCartByUserIdAndProductId(User user, Product product){return  cartRepository.findCartByUserIdAndProductId(user,product)
            .orElseThrow(()->new CustomException("Cart Not Found", ErrorCode.NotFoundCartException));
    }

    public List<CartUserListJoinResponseDTO>  getEqUserAndCart(Authentication authentication, String status){

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userEmail = principalDetails.getUser().getEmail();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new CustomException("",ErrorCode.NotFoundUserException));


        List<Cart> cartList =  cartRepository.getEqUserAndCart(user, status);
        List<CartUserListJoinResponseDTO> cartdtos = new ArrayList<>();
        //long totalsum = 0; // 최종 합계
        //int totalcarttotal =0;
        for(Cart cart: cartList){
            ProductReponseDTO productReponseDTO = ProductReponseDTO.builder()
                    .productId(cart.getProductId().getId())
                    .title(cart.getProductId().getTitle())
                    .name(cart.getProductId().getName())
                    .content(cart.getProductId().getContent())
                    .imgUrl(cart.getProductId().getImgUrl())
                    .price(cart.getProductId().getPrice())
                    .build();
            CartUserListJoinResponseDTO cartUserListJoinResponseDTO = CartUserListJoinResponseDTO.builder()
                    .cartId(cart.getId())
                    .product(productReponseDTO)
                    .productNum(cart.getProductNum())
                    .createDate(cart.getCreateTime())
                    .build();

            cartdtos.add(cartUserListJoinResponseDTO);
            // totalsum  = (totalsum + (cart.getProductNum() * cart.getProductId().getPrice()));
            // totalcarttotal = (int) (totalcarttotal +cart.getProductNum());

        }

        return cartdtos;

    }
}
