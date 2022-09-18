package com.project.shopping.service;

import com.project.shopping.model.Cart;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    //생성
    public Cart create(Cart cart){
        if(cart.getProductId()== null){
            throw new NoSuchElementException("해당 상품이 없습니다.");
        }
        return cartRepository.save(cart);
    }

    // 삭제
    public  void deleteCart(Cart cart){cartRepository.delete(cart);}
    // user가 등록한 장바구니 목록 조회
    public  List<Cart> findallByUserId(User user){return cartRepository.findAllByuserId(user); }

    // 유저  와 cartid를 이용한 해당 장바구니 상품 조회
    public Cart findCartUserAndId(User user, int id){
        return cartRepository.findCartByUserIdAndId(user, id);
    }


    public boolean existsCartByUserIdAndProductId(User user, Product product){return cartRepository.existsCartByUserIdAndProductId(user,product);}

    public Cart findCartByUserIdAndProductId(User user, Product product){return  cartRepository.findCartByUserIdAndProductId(user,product);}
}
