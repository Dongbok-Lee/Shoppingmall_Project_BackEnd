package com.project.shopping.repository;




import com.project.shopping.model.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class ReviewRepositoryImpl implements  ReviewRepositoryCustom{
    private  final JPAQueryFactory queryFactory;


    public ReviewRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QReview review = QReview.review;
    private BooleanExpression eqUserAndActive(User user, String status){
        if(review.status.eq(status) == null){
            return null;
        }
        if(review.userId.userId.eq(user.getUserId()) == null){
            return null;
        }

        return review.status.eq(status).and(review.userId.userId.eq(user.getUserId()));
    }

    private BooleanExpression eqProductAndActive(Product product, String status){
        if(review.status.eq(status) == null){
            return null;
        }
        if(review.productId.id.eq(product.getId()) == null){
            return null;
        }

        return review.status.eq(status).and(review.productId.id.eq(product.getId()));
    }

    @Override
    public List<Review> getEqUserAndActive(User user, String status) {
        return (List<Review>) queryFactory.from(review).where(eqUserAndActive(user,status)).fetch();
    }

    @Override
    public List<Review> getEqProductAndActive(Product product, String status) {
        return (List<Review>) queryFactory.from(review).where(eqProductAndActive(product,status)).fetch();
    }
}
