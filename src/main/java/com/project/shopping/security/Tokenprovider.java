package com.project.shopping.security;


import com.project.shopping.Error.ErrorCode;
import com.project.shopping.auth.PrincipalDetails;
import com.project.shopping.service.RedisService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class Tokenprovider {
    private  final RedisService redisService;
    private Key key;


    @Value("${jwt.secret}")
    private  String secretKey;

    @PostConstruct
    protected  void init(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // accessToken 재생성
    public Token reGenerateAccessToken(Authentication authentication){
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        long now = (new Date()).getTime();


        Date accessTokenExpireseIn = new Date(now + 1000*60*60*24); // 만료 시간 // 하루


        // 인증객체를 이용해서 email  뽑아 오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();

        // accessToken 생성
        String accessToken = Jwts.builder().setSubject(email).claim("auth",authorities).setExpiration(accessTokenExpireseIn).signWith(key, SignatureAlgorithm.HS256).compact();

        Token token = redisService.getValues(email+"jwtToken");
        token.setAccessToken(accessToken);

        redisService.setValue(email+"jwtToken",token);

        return token;

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

        //Access Token Refresh Token  만료 시간 정하기
        Date accessTokenExpireseIn = new Date(now + 1000*60*60*24); // 만료 시간 // 하루
        Date refreshTokenExpireseIn = new Date(now + 1000*60*60*24*10); // refreshToken 만료시간 // 10일



        // 인증객체를 이용해서 email  뽑아 오기
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String email = principalDetails.getUser().getEmail();

        String accessToken = Jwts.builder().setSubject(email).claim("auth",authorities).setExpiration(accessTokenExpireseIn).signWith(key, SignatureAlgorithm.HS256).compact();

        // Refresh Token 생성
        String refreshToekn = Jwts.builder().setSubject(email).setExpiration(refreshTokenExpireseIn).signWith(key, SignatureAlgorithm.HS256).compact();

        log.info("refreshToken생성",refreshToekn);
        Token token = Token.builder().accessToken(accessToken).refreshToken(refreshToekn).build();


        //redis에 refresh 토큰 저장
        redisService.setValues(email+"jwtToken",token,refreshTokenExpireseIn);

        return token;
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