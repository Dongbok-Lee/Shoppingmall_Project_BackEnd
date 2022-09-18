package com.project.shopping.controller;


import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.ReviewDTO;
import com.project.shopping.model.Product;
import com.project.shopping.model.Review;
import com.project.shopping.model.User;
import com.project.shopping.service.ProductService;
import com.project.shopping.service.ReviewService;
import com.project.shopping.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class ReviewController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/review/create/{id}")
    public ResponseEntity<?> createReview(Authentication authentication, @PathVariable(value = "id") int ProductId, @RequestBody ReviewDTO reviewDto){
        try{
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();

            // 로그인 정보로 user 찾기
            User user = userService.findEmailByUser(email);

            // 파라미터로 받은 값으로 상품 찾기
            Product product = productService.findproductid(ProductId);


            // 리뷰 생성
            Review review = Review.builder()
                    .userId(user)
                    .productId(product)
                    .title(reviewDto.getTitle())
                    .content(reviewDto.getContent())
                    .imageUrl(reviewDto.getImgUrl())
                    .reviewcreateTime(LocalDate.now())
                    .build();


            Review registeredReview = reviewService.create(review);


            // Review Dto 로 응답 보내기

            ReviewDTO response = ReviewDTO.builder()
                    .reviewId(review.getId())

                    .userEmail(registeredReview.getUserId().getEmail())
                    .userName(registeredReview.getUserId().getUsername())
                    .userNickName(registeredReview.getUserId().getNickname())
                    .productName(registeredReview.getProductId().getName())
                    .productId(registeredReview.getProductId().getId())
                    .productPrice(registeredReview.getProductId().getPrice())
                    .imgUrl(registeredReview.getImageUrl())
                    .title(reviewDto.getTitle())
                    .content(reviewDto.getContent())
                    .reviewCreateTime(registeredReview.getReviewcreateTime())
                    .build();

            return ResponseEntity.ok().body(response);



        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 리뷰 삭제
    @DeleteMapping("/review/delete/{id}")
    public ResponseEntity<?> reviewdelete(Authentication authentication, @PathVariable(value = "id") int ReviewId){ // reviewid
        try{
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email);
            Review findreview = reviewService.findReviewUserAndId(user, ReviewId);
            reviewService.deleteReview(findreview);

            ReviewDTO response = ReviewDTO.builder()
                    .imgUrl(findreview.getImageUrl())
                    .content(findreview.getContent())
                    .title(findreview.getTitle())
                    .build();
            return  ResponseEntity.ok().body(response);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 내가 등록한 리뷰 조회
    @GetMapping("/review/list/user")
    public ResponseEntity<?> findUserReviewlist(Authentication authentication){

        try {
            // 현재 로그인한 유저 찾기
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email);

            // 현재 나의 리뷰 목록 조회
            List<Review> userReviewlist = reviewService.findallByUserId(user);

            //dto 만들기
            List<ReviewDTO> userReviewListDto = new ArrayList<>();
            for (Review review : userReviewlist){
                ReviewDTO reviewDTO = ReviewDTO.builder()
                        .reviewId(review.getId())

                        .userEmail(review.getUserId().getEmail())
                        .userName(review.getUserId().getUsername())
                        .userAge(review.getUserId().getAge())
                        .userNickName(review.getUserId().getNickname())
                        .productName(review.getProductId().getName())
                        .productId(review.getProductId().getId())
                        .productPrice(review.getProductId().getPrice())
                        .imgUrl(review.getImageUrl())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .reviewCreateTime(review.getReviewcreateTime())
                        .build();
                userReviewListDto.add(reviewDTO);
            }

            Map<String , Object> result = new HashMap<>();
            result.put("data",userReviewListDto);
            return ResponseEntity.ok().body(result);

        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());

        }

    }

    // 상품에 달린 리뷰 조회
    @GetMapping("/review/list/product/{id}")
    public ResponseEntity<?> findProductReviewlist(@PathVariable(value = "id") int ProductId){
        try {
            // 상품 찾기
            Product product = productService.findproductid(ProductId);

            // 상품에 등록된 리뷰 찾기
            List<Review> ProductReviewList = reviewService.findallByProductId(product);
            List<ReviewDTO> ProductReviewDto = new ArrayList<>();

            for(Review review : ProductReviewList){
                ReviewDTO reviewDTO = ReviewDTO.builder()
                        .reviewId(review.getId())

                        .userEmail(review.getUserId().getEmail())
                        .userName(review.getUserId().getUsername())
                        .userAge(review.getUserId().getAge())
                        .userNickName(review.getUserId().getNickname())
                        .productName(review.getProductId().getName())
                        .productId(review.getProductId().getId())
                        .productPrice(review.getProductId().getPrice())
                        .imgUrl(review.getImageUrl())
                        .title(review.getTitle())
                        .content(review.getContent())
                        .reviewCreateTime(review.getReviewcreateTime())
                        .build();
                ProductReviewDto.add(reviewDTO);
            }
            Map<String, Object> result = new HashMap<>();
            result.put("data",ProductReviewDto);
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/review/update/{id}")
    public ResponseEntity<?> productUpdate(Authentication authentication, @PathVariable(value = "id") int reviewId, @RequestBody ReviewDTO reviewDTO){
        try{
            PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();
            String email = principalDetails.getUser().getEmail();
            User user = userService.findEmailByUser(email); // 유저 찾기

            Review findReview = reviewService.findReviewUserAndId(user,reviewId); // 유저와 해당 리뷰 아이디로 리뷰 찾기
            if(reviewDTO.getContent() != ""){
                findReview.setContent(reviewDTO.getContent());
            }
            if(reviewDTO.getTitle() != ""){
                findReview.setTitle(reviewDTO.getTitle());
            }
            if(reviewDTO.getImgUrl() != ""){
                findReview.setImageUrl(reviewDTO.getImgUrl());
            }

            findReview.builder().reviewcreateTime(LocalDate.now());


            Review review = reviewService.create(findReview);

            ReviewDTO response = ReviewDTO.builder()
                    .reviewId(review.getId())
                    .userEmail(review.getUserId().getEmail())
                    .userName(review.getUserId().getUsername())
                    .userNickName(review.getUserId().getNickname())
                    .productName(review.getProductId().getName())
                    .productId(review.getProductId().getId())
                    .productPrice(review.getProductId().getPrice())
                    .imgUrl(review.getImageUrl())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .reviewCreateTime(review.getReviewcreateTime())
                    .build();

            return  ResponseEntity.ok().body(response);

        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());

        }

    }





}
