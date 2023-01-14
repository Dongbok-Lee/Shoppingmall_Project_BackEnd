package com.project.shopping.Error;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCusomException(CustomException e){
        ErrorResponse response = new ErrorResponse(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("handleException",ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.BadParameterException);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadablException(HttpMessageNotReadableException e){
        log.warn("HttpMessageNotReadableException", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.BadParameterException);
        return  new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException e){
        log.warn("MethodArgumentNotValidException",e);
        ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());

        return  new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));

    }


    private ErrorResponse makeErrorResponse(BindingResult bindingResult) {
        ErrorResponse response = null;

        if(bindingResult.hasErrors()){
            String bindResultCode = bindingResult.getFieldError().getCode();
            switch (bindResultCode){
                case "NotNull":
                case "NotBlank":
                    response = new ErrorResponse(ErrorCode.BadParameterException);
                    break;
                case "Email":
                    response = new ErrorResponse(ErrorCode.BadEmailException);
                    break;
                case "Pattern":
                    response = new ErrorResponse(ErrorCode.BadPhoneNumberException);
                    break;

            }

        }
        return response;
    }
}
