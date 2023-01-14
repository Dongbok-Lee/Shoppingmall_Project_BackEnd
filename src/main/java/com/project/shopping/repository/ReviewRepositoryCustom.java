package com.project.shopping.repository;

import com.project.shopping.model.Product;
import com.project.shopping.model.Review;
import com.project.shopping.model.User;

import java.util.List;

public interface ReviewRepositoryCustom {
    List<Review> getEqUserAndActive(User user, String status);

    List<Review> getEqProductAndActive(Product product , String status);
}
