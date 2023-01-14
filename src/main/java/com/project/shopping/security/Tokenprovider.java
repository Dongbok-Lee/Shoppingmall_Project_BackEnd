package com.project.shopping.security;


import com.project.shopping.Error.ErrorCode;
import com.project.shopping.Error.ErrorResponse;
import com.project.shopping.model.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@Slf4j
public class Tokenprovider {
    private  static final String SECRET_KEY ="NMA8JPctFuna59f511ffff23257uuiuffff54ak";

    @Autowired
    private  RefreshTokenRepsitory refreshTokenRepsitory;

    Date now = new Date();
    long tokenPeriod = 1000L * 60L * 10L;
    long refreshPeriod = 1000L * 60L * 60L * 24L * 30L * 3L;
    // 토큰 생성 api
    public String create(User user){
        Date expiryDate = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
        Date expiryDaterefresh =Date.from(Instant.now().plus(3, ChronoUnit.DAYS));

        Token token = new Token(Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .setSubject(user.getEmail())
                .setIssuer("shopping")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact(),
                Jwts.builder()
                        .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                        .setSubject(user.getEmail())
                        .setIssuer("shopping")
                        .setIssuedAt(new Date())
                        .setExpiration(expiryDaterefresh)
                        .compact());
        refreshTokenRepsitory.save(token);

        return token.getToken();


    }
    public boolean booleanexp (String token){
        boolean booltoken = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration().after(new Date());
        return booltoken;
    }

    // 토큰 만료시간 api
    public String verifyToken(String token, User user){

        Token existstoken = refreshTokenRepsitory.findByToken(token); // 토큰을 찾고
        String accessToken = existstoken.getToken();
        String refreshToken = existstoken.getRefreshToken();
        boolean existsaccessexp = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody().getExpiration().after(new Date());
        boolean existsrefreshexp = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(refreshToken).getBody().getExpiration().after(new Date());

        Token token1 = existstoken;
        if(!existsaccessexp){
            if(existsrefreshexp){

                token1 = new Token(Jwts.builder()
                        .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                        .setSubject(user.getEmail())
                        .setIssuer("shopping")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(tokenPeriod+now.getTime()))
                        .compact(),
                        Jwts.builder()
                                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                                .setSubject(user.getEmail())
                                .setIssuer("shopping")
                                .setIssuedAt(new Date())
                                .setExpiration(new Date(refreshPeriod+now.getTime()))
                                .compact());

            }
        }
        return token1.getToken();
    }



        // 토큰이 있는지 검사하고 이메일 추출하는 api
    public String validateAndGetUserEmail(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // refresh 새로 생성 , 검증 로직  , 여기 자체서 에러 5000


}
