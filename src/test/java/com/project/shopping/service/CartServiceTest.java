package com.project.shopping.service;

import com.project.shopping.model.Cart;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.repository.CartRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @Autowired
    private  ProductService productService;
    @Autowired
    private  UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Test
    public void createCart(){
        String passworddd ="asdfasfdasdf";
        User user = User.builder()
                .email("cart1@example.com")
                .username("user")
                .password(passwordEncoder.encode((passworddd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .postCode("postcode")
                .build();
        User createUser = userService.create(user);
        Product product = Product.builder()
                .userId(createUser)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);
        Cart cart = Cart.builder().userId(createUser).productId(createProduct).createTime(Timestamp.valueOf(LocalDateTime.now())).build();
        Cart  creatCart = cartService.create(cart);

        Assertions.assertThat(cart.getId()).isEqualTo(creatCart.getId());




    }
    @Test
    public  void findCartUserAndId(){
        String passworddd ="asdfasfdasdf";
        User user = User.builder()
                .email("cart2@example.com")
                .username("user")
                .password(passwordEncoder.encode((passworddd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .postCode("test")
                .build();
        User createUser = userService.create(user);
        Product product = Product.builder()
                .userId(createUser)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);
        Cart cart = Cart.builder().userId(createUser).productId(createProduct).createTime(Timestamp.valueOf(LocalDateTime.now())).build();
        Cart  creatCart = cartService.create(cart); //카트 생성

        Cart findcart = cartService.findCartUserAndId(user, cart.getId());
        Assertions.assertThat(findcart.getId()).isEqualTo(creatCart.getId());

    }

    @Test
    public void findallByUserId(){
        String passworddd ="asdfasfdasdf";
        User user = User.builder()
                .email("cart3@example.com")
                .username("user")
                .password(passwordEncoder.encode((passworddd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .postCode("test")
                .build();
        User createUser = userService.create(user);
        Product product = Product.builder()
                .userId(createUser)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);
        Cart cart = Cart.builder().userId(createUser).productId(createProduct).createTime(Timestamp.valueOf(LocalDateTime.now())).build();
        Cart  creatCart = cartService.create(cart); //카트 생성

        List<Cart> cartList = cartService.findallByUserId(createUser);
        for(Cart carts : cartList){
            if(carts.getUserId().getUserId() == user.getUserId()){
                Assertions.assertThat(cart.getUserId().getEmail()).isEqualTo(user.getEmail());
            }
        }

    }

    @Test
    public void delete(){
        String passworddd ="asdfasfdasdf";
        User user = User.builder()
                .email("cart4@example.com")
                .username("user")
                .password(passwordEncoder.encode((passworddd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .postCode("test")
                .build();
        User createUser = userService.create(user);
        Product product = Product.builder()
                .userId(createUser)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);
        Cart cart = Cart.builder().userId(createUser).productId(createProduct).createTime(Timestamp.valueOf(LocalDateTime.now())).build();
        Cart  creatCart = cartService.create(cart); //카트 생성

        Cart findcart = cartService.findCartUserAndId(createUser,creatCart.getId());
        cartService.deleteCart(findcart);




        Optional<Cart> deleteProduct = Optional.ofNullable(cartService.findCartUserAndId(createUser, creatCart.getId()));
        org.junit.jupiter.api.Assertions.assertFalse(deleteProduct.isPresent());
    }
}
