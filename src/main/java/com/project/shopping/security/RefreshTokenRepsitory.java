package com.project.shopping.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepsitory extends JpaRepository<Token, String> {
    Token findByToken(String token);
}
