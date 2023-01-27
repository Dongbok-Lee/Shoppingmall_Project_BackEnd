package com.project.shopping.service;

import com.project.shopping.Error.CustomException;
import com.project.shopping.Error.ErrorCode;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.dto.requestDTO.RevieRequestDTO.ReviewCreateRequestDTO;
import com.project.shopping.dto.requestDTO.RevieRequestDTO.ReviewUpdateRequestDTO;
import com.project.shopping.dto.responseDTO.ReviewResponseDTO.*;
import com.project.shopping.model.Product;
import com.project.shopping.model.Review;
import com.project.shopping.model.User;
import com.project.shopping.repository.ProductRepository;
import com.project.shopping.repository.ReviewRepository;
import com.project.shopping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    // 삭제 , 내 리뷰목록 조회 , 생성 , 상품 리뷰목록  조회


    private final  ReviewRepository reviewrepository;
    private  final UserRepository userRepository;
    private  final ProductRepository productRepository;

    // 생성
    public ReviewCreateResponseDTO create(Authentication authentication, int ProductId, ReviewCreateRequestDTO reviewCreateRequestDTO){

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();

        // 로그인 정보로 user 찾기
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("User Not Fount", ErrorCode.NotFoundUserException));

        // 파라미터로 받은 값으로 상품 찾기
        Product product = productRepository.findById(ProductId)
                .orElseThrow(()-> new CustomException("Product Not Found", ErrorCode.NotFoundProductException));


        // 리뷰 생성
        Review review = Review.builder()
                .userId(user)
                .productId(product)
                .title(reviewCreateRequestDTO.getTitle())
                .content(reviewCreateRequestDTO.getContent())
                .imageUrl(reviewCreateRequestDTO.getImgUrl())
                .status("active")
                .build();

        reviewrepository.save(review);
        ReviewCreateResponseDTO reviewCreateResponseDTO = ReviewCreateResponseDTO.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .build();


        return reviewCreateResponseDTO;
    }


    public ReviewUpdateResponseDTO update(Authentication authentication, int reviewId, ReviewUpdateRequestDTO reviewUpdateRequestDTO){
        PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("User Not Fount", ErrorCode.NotFoundUserException));// 유저 찾기

        Review findReview = reviewrepository.findByUserIdAndId(user,reviewId)
                .orElseThrow(()-> new CustomException("Review Not Found", ErrorCode.NotFoundReviewException));// 유저와 해당 리뷰 아이디로 리뷰 찾기

        findReview.setContent(reviewUpdateRequestDTO.getContent());
        findReview.setTitle(reviewUpdateRequestDTO.getTitle());
        findReview.setImageUrl(reviewUpdateRequestDTO.getImgUrl());

        // 수정하기
        if(findReview.getProductId().getStatus().equals("Disabled")){
            throw new CustomException("Product Not Found", ErrorCode.NotFoundProductException);
        }

        reviewrepository.save(findReview);
        ReviewUpdateResponseDTO reviewUpdateResponseDTO = ReviewUpdateResponseDTO.builder()
                .reviewId(findReview.getId())
                .title(findReview.getTitle())
                .content(findReview.getContent())
                .imgUrl(findReview.getImageUrl())
                .build();


        return reviewUpdateResponseDTO;}



    // 내 리뷰 목록 죄히
    public List<Review> findallByUserId(User user){return  reviewrepository.findAllByUserId(user);}

    // 상품 리뷰 목록 조회
    public List<Review> findallByProductId(Product product){return  reviewrepository.findAllByProductId(product);}

    // 삭제
    public ReviewDeleteResponseDTO deleteReview(Authentication authentication,int ReviewId){

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("User Not Fount", ErrorCode.NotFoundUserException));

        Review findreview = reviewrepository.findByUserIdAndId(user, ReviewId)
                .orElseThrow(()-> new CustomException("Review Not Found", ErrorCode.NotFoundReviewException));
        findreview.setStatus("Disabled");

        reviewrepository.save(findreview);


        ReviewDeleteResponseDTO reviewDeleteResponseDTO = ReviewDeleteResponseDTO.builder()
                .reviewId(findreview.getId())
                .content(findreview.getContent())
                .title(findreview.getTitle())
                .build();

        return reviewDeleteResponseDTO;
    }


    public Review findReviewUserAndId(User user, int id){
        return reviewrepository.findByUserIdAndId(user,id)
                .orElseThrow(()-> new CustomException("Review Not Found", ErrorCode.NotFoundReviewException));
    }
    public Boolean existReviewUserAndId(User user, int id){return reviewrepository.existsByUserIdAndId(user,id);}

    public List<ReviewUserJoinResponseDTO>  getEqUserAndActive(Authentication authentication, String status){

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new CustomException("User Not Fount", ErrorCode.NotFoundUserException));
        List<Review> userReviewlist = reviewrepository.getEqUserAndActive(user, status);
        //dto 만들기
        List<ReviewUserJoinResponseDTO> userReviewListDto = new ArrayList<>();
        for (Review review : userReviewlist){
            ReviewUserJoinResponseDTO reviewUserJoinResponseDTO = ReviewUserJoinResponseDTO.builder()
                    .reviewId(review.getId())
                    .content(review.getContent())
                    .title(review.getTitle())
                    .createDate(review.getCreateDate())
                    .modifiedDate(review.getModifiedDate())
                    .imgUrl(review.getImageUrl())
                    .build();
            userReviewListDto.add(reviewUserJoinResponseDTO);
        }
        return userReviewListDto;
    }

    public List<ReviewProductJoinResponseDTO> getEqProductAndActive(int ProductId, String status){
        Product product = productRepository.findById(ProductId)
                .orElseThrow(()-> new CustomException("Product Not Found", ErrorCode.NotFoundProductException));
        List<Review> ProductReviewList = reviewrepository.getEqProductAndActive(product,status);

        List<ReviewProductJoinResponseDTO> ProductReviewDto = new ArrayList<>();

        for(Review review : ProductReviewList){
            ReviewProductJoinResponseDTO reviewProductJoinResponseDTO = ReviewProductJoinResponseDTO.builder()
                    .reviewId(review.getId())
                    .userNickname(review.getUserId().getNickname())
                    .imgURL(review.getImageUrl())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .createDate(review.getCreateDate())
                    .modifiedDate(review.getModifiedDate())
                    .build();
            ProductReviewDto.add(reviewProductJoinResponseDTO);
        }

        return  ProductReviewDto;
    }
}
