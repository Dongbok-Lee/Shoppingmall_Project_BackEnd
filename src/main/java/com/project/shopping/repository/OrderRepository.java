package com.project.shopping.repository;

import com.project.shopping.model.Order;
import com.project.shopping.model.Product;
import com.project.shopping.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order,String> {
    List<Order> findAllByUser(User user);
}
