package com.microservice.exceptionManagement;

import com.microservice.constants.ExceptionCodeEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus httpStatus;

    public BaseException(ExceptionCodeEnum exceptionCode) {
        super(exceptionCode.getMessage());
        this.errorCode = exceptionCode.getCode();
        this.httpStatus = exceptionCode.getHttpStatus();
    }

    public BaseException(ExceptionCodeEnum exceptionCode, Throwable cause) {
        super(exceptionCode.getMessage(), cause);
        this.errorCode = exceptionCode.getCode();
        this.httpStatus = exceptionCode.getHttpStatus();
    }
}
