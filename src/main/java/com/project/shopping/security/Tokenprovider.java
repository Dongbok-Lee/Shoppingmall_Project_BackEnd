package com.project.shopping.security;


import com.project.shopping.Error.ErrorCode;
import com.project.shopping.Error.ErrorResponse;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Tokenprovider {
    private Key key;


    public Tokenprovider(@Value("${jwt.secret}")String secretKey){
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // 토큰으로 부터 email 출력
    public String TokenInfo(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    //유저 정보를 가지고 AccessToken, RefreshToken을 생성하는 메소드
    public Token generateToken(Authentication authentication){
        //권한 정보 가져오기
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();

        //Access Token 생성
        Date accessTokenExpireseIn = new Date(now + 86400000); // 만료 시간


        // 인증객체를 이용해서 email  뽑아 오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();

        String accessToken = Jwts.builder().setSubject(email).claim("auth",authorities).setExpiration(accessTokenExpireseIn).signWith(key, SignatureAlgorithm.HS256).compact();

        // Refresh Token 생성
        String refreshToekn = Jwts.builder().setExpiration(new Date(now +86400000)).signWith(key, SignatureAlgorithm.HS256).compact();

        return Token.builder().accessToken(accessToken).refreshToken(refreshToekn).build();
    }

    // 토큰 정보 검증 메소드
    public boolean validateToken(String token, HttpServletRequest request){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            e.printStackTrace();
            log.info("exception",e);
            request.setAttribute("exception", ErrorCode.UnauthorizedException.getErrorCode());

        }catch (ExpiredJwtException e){
            e.printStackTrace();
            request.setAttribute("exception",ErrorCode.ExpirationException.getErrorCode());
            log.info("Expired Jwt Token",e);

        }catch (UnsupportedJwtException e){
            log.info("Unsupported JWT token",e);
            request.setAttribute("exception", ErrorCode.UnauthorizedException.getErrorCode());

        }
        catch (IllegalArgumentException e){
            log.info("JWT claims string is empty", e);
            request.setAttribute("exception", ErrorCode.UnauthorizedException.getErrorCode());

        }return false;
    }


    // refresh 새로 생성 , 검증 로직  , 여기 자체서 에러 5000


}
