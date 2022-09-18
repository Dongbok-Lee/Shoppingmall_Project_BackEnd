package com.project.shopping.repository;

import com.project.shopping.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getProductList(String tilte);
}
