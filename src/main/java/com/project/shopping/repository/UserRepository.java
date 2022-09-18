package com.project.shopping.repository;

import com.project.shopping.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //save(), findOne(), findAll(), count(), delete() 제공
    User findByEmail(String email);
    Boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);

}
