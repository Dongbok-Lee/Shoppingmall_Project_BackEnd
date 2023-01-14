package com.project.shopping.repository;

import com.project.shopping.model.Product;
import com.project.shopping.model.Review;
import com.project.shopping.model.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review,Integer>, ReviewRepositoryCustom {
    // 삭제 , 내 리뷰목록 조회 , 생성 , 상품 리뷰목록  조회

    List<Review> findAllByUserId(User userId); // 내모든 리뷰 목록 조회
    List<Review> findAllByProductId(Product product); // 상품 모든 리뷰 목록 조회

    // 삭제를 위한 user와 리뷰아이디로 리뷰 찾기
    Optional<Review> findByUserIdAndId(User userId, int id);

    Boolean existsByUserIdAndId(User userId, int id);

    @Override
    List<Review> getEqUserAndActive(User user, String status);

    @Override
    List<Review> getEqProductAndActive(Product product, String status);
}
