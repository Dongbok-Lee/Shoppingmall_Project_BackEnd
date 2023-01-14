package com.project.shopping.Error;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private  int status;
    private  String message;
    private  String errorCode;

    @Builder
    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getErrorCode();
    }
}
