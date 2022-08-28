package com.project.shopping.service;

import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import com.project.shopping.repository.UserRepository;
import org.assertj.core.api.Assertions;
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
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void Create(){
        String passwordd ="asdfasfdasdf";
        User user = User.builder()
                .email("user3@example.com")
                .username("user")
                .password(passwordEncoder.encode((passwordd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .build();
        userService.create(user);
        Product product = Product.builder()
                .userId(user)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        Product createProduct = productService.create(product);

        Assertions.assertThat(product.getId()).isEqualTo(createProduct.getId());
    }
    @Test
    public void findallProduct(){
        String passwordd ="asdfasfdasdf";
        User user = User.builder()
                .email("user4@example.com")
                .username("user")
                .password(passwordEncoder.encode((passwordd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .build();
        userService.create(user);
        Product product = Product.builder()
                .userId(user)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);

        List<Product> productlist = productService.findall();
        for(Product products : productlist){
            if(products.getId() == createProduct.getId()){
                Assertions.assertThat(products.getId()).isEqualTo(createProduct.getId());
            }
        }

    }

    @Test
    public void findproductId(){
        String passwordd ="asdfasfdasdf";
        User user = User.builder()
                .email("user5@example.com")
                .username("user")
                .password(passwordEncoder.encode((passwordd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .build();
        userService.create(user);
        Product product = Product.builder()
                .userId(user)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);
        Product findproductId = productService.findproductid(createProduct.getId());
        Assertions.assertThat(findproductId.getId()).isEqualTo(createProduct.getId());

    }

    @Test
    public void deleteProduct(){
        String passwordd ="asdfasfdasdf";
        User user = User.builder()
                .email("user6@example.com")
                .username("user")
                .password(passwordEncoder.encode((passwordd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .build();
        userService.create(user);
        Product product = Product.builder()
                .userId(user)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);
        User finduser = userService.findEmailByUser(user.getEmail());
        Product findProduct = productService.findProductNameUser(createProduct.getId(),finduser);
        productService.deleteProduct(findProduct);
        Optional<Product> deleteProduct = Optional.ofNullable(productService.findproductid(createProduct.getId()));
        org.junit.jupiter.api.Assertions.assertFalse(deleteProduct.isPresent());

    }

    @Test
    public void getProductList(){
        String passwordd ="asdfasfdasdf";
        User user = User.builder()
                .email("user7@example.com")
                .username("user")
                .password(passwordEncoder.encode((passwordd)))
                .address("address")
                .age(11)
                .nickname("nickname")
                .phoneNumber("01000000000")
                .build();
        userService.create(user);
        Product product = Product.builder()
                .userId(user)
                .title("가나다")
                .name("치킨")
                .content("clzls")
                .price(10000)
                .total(1000)
                .imgUrl("asdfasdf")
                .createDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        Product createProduct = productService.create(product);

        List<Product> productlist = productService.getProductList("가나다");
        for(Product products : productlist){
            if(products.getId() == createProduct.getId()){
                Assertions.assertThat(products.getId()).isEqualTo(createProduct.getId());
            }
        }

    }





}
