package com.project.shopping.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
public enum ErrorCode {
    BadPasswordException(400,"BadParameterException","잘못된 password 입니다"),
    BadParameterException(400,"BadParameterException","잘못된 형식의 데이터 입니다."),

    BadEmailException(400,"BadEmailException","잘못된 형식의 이메일 입니다"),
    BadPhoneNumberException(400,"BadPhoneNumberException","잘못된 형식의 번호입니다"),
    UnauthorizedException(401,"UnauthorizedException","허용되지 않는 접근입니다."),
    NotFoundUserException(404,"NotFoundException","계정이 존재하지 않습니다"),
    NotFoundProductException(404,"NotFoundException","상품이 존재하지 않습니다"),
    NotFoundCartException(404,"NotFoundException","해당 장바구니가 존재하지 않습니다"),
    NotFoundCartNumException(404,"NotFoundException","상품의 구매한도를 초과했습니다"),
    NotFoundCartNumDownException(404,"NotFoundException","상품이 1 미만 일 수 없습니다."),
    NotFoundOrderException(404,"NotFoundException","”해당 주문이 존재하지 않습니다.”"),
    NotFoundReviewException(404,"NotFoundException","”해당 리뷰가 존재하지 않습니다.”"),
    DuplicatedEmilException(409,"DuplicatedParameterException","이미 존재하는 이메일입니다."),
    DuplicatedNickNameException(409,"DuplicatedParameterException","이미 존재하는 닉네임입니다."),
    INTER_SERVER_ERROR(500,"COMMON-ERR-500","INTER SERVER ERROR"),
    ;

    private int status;
    private String errorCode;
    @Setter
    private String message;


}
