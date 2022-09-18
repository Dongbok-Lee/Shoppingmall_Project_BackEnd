package com.project.shopping.service;

import com.project.shopping.model.Product;
import com.project.shopping.model.Review;
import com.project.shopping.model.User;
import com.project.shopping.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReviewService {
    // 삭제 , 내 리뷰목록 조회 , 생성 , 상품 리뷰목록  조회

    @Autowired
    private ReviewRepository reviewrepository;

    // 생성
    public Review create(Review review){
        if(review.getProductId() == null){
            throw new NoSuchElementException("해당 상품이 없습니다.");
        }
        return reviewrepository.save(review);}

    // 내 리뷰 목록 죄히
    public List<Review> findallByUserId(User user){return  reviewrepository.findAllByUserId(user);}

    // 상품 리뷰 목록 조회
    public List<Review> findallByProductId(Product product){return  reviewrepository.findAllByProductId(product);}

    // 삭제
    public void deleteReview(Review review){ reviewrepository.delete(review);}


    public Review findReviewUserAndId(User user, int id){
        return reviewrepository.findByUserIdAndId(user,id);
    }

}
