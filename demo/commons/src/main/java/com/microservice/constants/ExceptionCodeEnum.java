package com.microservice.constants;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCodeEnum {

    UNEXPECTED_AUTHENTICATION("ER001", "Bilinmeyen kimlik doğrulama hatası", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("ER002", "Yetkisiz erişim", HttpStatus.UNAUTHORIZED),
    FORBIDDEN("ER003", "Erişim engellendi", HttpStatus.FORBIDDEN),

    USER_NOT_FOUND("ER100", "User not found.", HttpStatus.NOT_FOUND);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ExceptionCodeEnum(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
