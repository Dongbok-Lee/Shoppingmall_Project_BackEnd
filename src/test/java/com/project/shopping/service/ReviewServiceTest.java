package com.project.shopping.service;

import com.project.shopping.model.Product;
import com.project.shopping.model.Review;
import com.project.shopping.model.User;
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
public class ReviewServiceTest {

//
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private  ReviewService reviewService;

//    @Test
//    public void createReview(){
//        String passwordd ="asdfasfdasdf";
//        User user = User.builder()
//                .email("review1@example.com")
//                .username("user")
//                .password(passwordEncoder.encode((passwordd)))
//                .address("address")
//                .age(11)
//                .nickname("nickname")
//                .phoneNumber("01000000000")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .postCode("postCode")
//                .build();
//        userService.create(user); // user 생성
//
//        Product product = Product.builder()
//                .userId(user)
//                .title("가나다")
//                .name("치킨")
//                .content("clzls")
//                .price(10000)
//                .total(1000)
//                .imgUrl("asdfasdf")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//
//        productService.create(product); // product 생성
//
//
//        Review review = Review.builder().userId(user).productId(product).title("가나다").content("가나다").imageUrl("asdf").status("active").build();
//
//        Review createReview = reviewService.create(review);
//
//        Assertions.assertThat(createReview.getId()).isEqualTo(review.getId());
//
//
//    }
//
//    @Test
//    public void findallByUserId(){
//        String passwordd ="asdfasfdasdf";
//        User user = User.builder()
//                .email("review2@example.com")
//                .username("user")
//                .password(passwordEncoder.encode((passwordd)))
//                .address("address")
//                .age(11)
//                .nickname("nickname")
//                .phoneNumber("01000000000")
//                .postCode("postCode")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//        userService.create(user); // user 생성
//
//        Product product = Product.builder()
//                .userId(user)
//                .title("가나다")
//                .name("치킨")
//                .content("clzls")
//                .price(10000)
//                .total(1000)
//                .imgUrl("asdfasdf")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//
//        productService.create(product); // product 생성
//
//
//        Review review = Review.builder().userId(user).productId(product).title("가나다").content("가나다").imageUrl("asdf").status("active").build();
//
//        Review createReview = reviewService.create(review);
//
//        List<Review> findallReviewUserId = reviewService.findallByUserId(user); // 해당 유저 가 단 리뷰 조회
//        for(Review reviews : findallReviewUserId){
//            Assertions.assertThat(reviews.getId()).isEqualTo(createReview.getId());
//        }
//    }
//
//    @Test
//    public void findallByProductId(){
//        String passwordd ="asdfasfdasdf";
//        User user = User.builder()
//                .email("review3@example.com")
//                .username("user")
//                .password(passwordEncoder.encode((passwordd)))
//                .address("address")
//                .age(11)
//                .nickname("nickname")
//                .phoneNumber("01000000000")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .postCode("postCode")
//                .build();
//        userService.create(user); // user 생성
//
//        Product product = Product.builder()
//                .userId(user)
//                .title("가나다")
//                .name("치킨")
//                .content("clzls")
//                .price(10000)
//                .total(1000)
//                .imgUrl("asdfasdf")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//
//        productService.create(product); // product 생성
//
//
//        Review review = Review.builder().userId(user).productId(product).title("가나다").content("가나다").imageUrl("asdf").status("active").build();
//
//        Review createReview = reviewService.create(review);
//        List<Review> findReviewProductId = reviewService.findallByProductId(product);
//        for(Review reviews : findReviewProductId){
//            Assertions.assertThat(reviews.getId()).isEqualTo(createReview.getId());
//        }
//
//    }
//
//    @Test
//    public void deleteReview(){
//        String passwordd ="asdfasfdasdf";
//        User user = User.builder()
//                .email("review4@example.com")
//                .username("user")
//                .password(passwordEncoder.encode((passwordd)))
//                .address("address")
//                .age(11)
//                .nickname("nickname")
//                .phoneNumber("01000000000")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .postCode("postCode")
//                .build();
//        userService.create(user); // user 생성
//
//        Product product = Product.builder()
//                .userId(user)
//                .title("가나다")
//                .name("치킨")
//                .content("clzls")
//                .price(10000)
//                .total(1000)
//                .imgUrl("asdfasdf")
//                .status("active")
//                .createDate(Timestamp.valueOf(LocalDateTime.now()))
//                .build();
//
//        productService.create(product); // product 생성
//
//
//        Review review = Review.builder().userId(user).productId(product).title("가나다").content("가나다").imageUrl("asdf").status("active").build();
//
//        Review createReview = reviewService.create(review);
//        Review findreview = reviewService.findReviewUserAndId(user, review.getId());
//        reviewService.deleteReview(findreview);
//
//
//        Optional<Review> deleteReview = Optional.ofNullable(reviewService.findReviewUserAndId(user, createReview.getId()));
//        org.junit.jupiter.api.Assertions.assertFalse(deleteReview.isPresent());
//
//    }


}
