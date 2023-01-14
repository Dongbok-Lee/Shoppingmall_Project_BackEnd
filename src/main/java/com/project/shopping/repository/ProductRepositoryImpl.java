package com.project.shopping.repository;

import com.project.shopping.model.Product;
import  com.project.shopping.model.QProduct;
import com.project.shopping.model.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.util.StringUtils;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private  final JPAQueryFactory queryFactory;



    public ProductRepositoryImpl(JPAQueryFactory queryFactory){
        this.queryFactory = queryFactory;
    }
    QProduct product = QProduct.product;

    private BooleanExpression eqName(String name, String status){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        return product.name.contains(name).and(product.status.eq(status));
    }

    private  BooleanExpression eqprice(String price, String status){
        if(StringUtils.isEmpty(price)){
            return null;
        }
        try{
            return product.price.eq(Long.valueOf(price)).and(product.status.eq(status));
        }catch (Exception e){
            return null;
        }

    }
    private  BooleanExpression eqregistrationusername(String username, String status){
        if(StringUtils.isEmpty(username)){
            return  null;
        }
        return product.userId.username.contains(username).and(product.status.eq(status));
    }
    private BooleanExpression eqProductTiltle(String title, String status){
        if(StringUtils.isEmpty(title)){
            return null;
        }
        return product.title.contains(title).and(product.status.eq(status));
    }



    private BooleanExpression eqProductStatus(String status){
        if(product.status.eq(status) == null){
            return null;
        }

        return product.status.eq(status);
    }
    private BooleanExpression eqUserAndActive(User user, String status){
        if(product.status.eq(status) == null){
            return null;
        }
        if(product.userId.userId.eq(user.getUserId()) == null){
            return null;
        }

        return product.status.eq(status).and(product.userId.userId.eq(user.getUserId()));
    }


    @Override
    public List<Product> getProductList(String searchparam, String status) {
        return (List<Product>) queryFactory.from(product).where(eqName(searchparam,status).or(eqProductTiltle(searchparam,status))
                .or(eqregistrationusername(searchparam,status)).or(eqprice(searchparam,status))
        ).fetch();
    }


    @Override
    public List<Product> getActiveProdcutList(String status) {
        System.out.println(status + "reipmle");
        return (List<Product>) queryFactory.from(product).where(eqProductStatus(status)).fetch();
    }

    @Override
    public List<Product> getEqUserAndActive(User user, String status) {
        return (List<Product>) queryFactory.from(product).where(eqUserAndActive(user,status)).fetch();
    }
}
