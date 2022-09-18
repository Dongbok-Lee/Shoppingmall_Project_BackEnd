package com.project.shopping.repository;

import com.project.shopping.model.Product;
import  com.project.shopping.model.QProduct;
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

    private BooleanExpression eqName(String name){
        if(StringUtils.isEmpty(name)){
            return null;
        }
        return product.name.contains(name);
    }

    private  BooleanExpression eqprice(String price){
        if(StringUtils.isEmpty(price)){
            return null;
        }
        try{
            return product.price.eq(Long.valueOf(price));
        }catch (Exception e){
            return null;
        }

    }
    private  BooleanExpression eqregistrationusername(String username){
        if(StringUtils.isEmpty(username)){
            return  null;
        }
        return product.userId.username.contains(username);
    }
    private BooleanExpression eqProductTiltle(String title){
        if(StringUtils.isEmpty(title)){
            return null;
        }
        return product.title.contains(title);
    }



    @Override
    public List<Product> getProductList(String searchparam) {
        return (List<Product>) queryFactory.from(product).where(eqName(searchparam).or(eqProductTiltle(searchparam))
                .or(eqregistrationusername(searchparam)).or(eqprice(searchparam))
        ).fetch();
    }
}
