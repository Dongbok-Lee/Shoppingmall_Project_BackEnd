package com.project.shopping.repository;

import com.project.shopping.model.Cart;
import com.project.shopping.model.User;

import java.util.List;

public interface CartRepositoryCustom {

    List<Cart> getEqUserAndCart(User user, String status);
}
