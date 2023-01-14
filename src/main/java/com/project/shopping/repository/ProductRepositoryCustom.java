package com.project.shopping.repository;

import com.project.shopping.model.Product;
import com.project.shopping.model.User;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getProductList(String tilte, String status);


    List<Product> getActiveProdcutList(String status);

    List<Product> getEqUserAndActive(User user, String status);

}
