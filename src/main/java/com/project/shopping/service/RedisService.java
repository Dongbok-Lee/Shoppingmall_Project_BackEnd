package com.project.shopping.service;

import com.project.shopping.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class RedisService {
    private  final RedisTemplate<String, Token> redisTemplate;

    // refresh 토큰 생성 및 업데이트
    public void setValues(String key, Token token, Date refreshTokenExpireseIn){
        ValueOperations<String, Token> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, token);
    }

    public void setValue(String key, Token token){
        ValueOperations<String, Token> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, token);
    }

    // key를 이용한 refresh 토큰 읽기
    public Token getValues(String key){
        ValueOperations<String, Token> valueOperations = redisTemplate.opsForValue();
        Token refreshToken = valueOperations.get(key);
        return refreshToken;
    }

    // 로그아웃시 refresh  토큰 삭제
    public void deleteValues(String key){
        redisTemplate.delete(key);
    }
}
