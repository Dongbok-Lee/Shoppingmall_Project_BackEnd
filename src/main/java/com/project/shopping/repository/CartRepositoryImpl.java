package com.project.shopping.repository;

import com.project.shopping.model.Cart;
import com.project.shopping.model.Product;
import com.project.shopping.model.QCart;
import com.project.shopping.model.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class CartRepositoryImpl implements  CartRepositoryCustom{
    private  final JPAQueryFactory queryFactory;

    public CartRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    QCart cart = QCart.cart;

    private BooleanExpression eqUserandActive(User user, String status){
        if(cart.status.eq(status) == null){
            return null;
        }
        if(cart.userId.userId.eq(user.getUserId()) == null){
            return null;
        }
        return cart.userId.userId.eq(user.getUserId()).and(cart.status.eq(status));
    }


    @Override
    public List<Cart> getEqUserAndCart(User user, String status) {
        return (List<Cart>) queryFactory.from(cart).where(eqUserandActive(user,status)).fetch();
    }
}
