package com.microservice.exceptionManagement;

import com.microservice.exceptionManagement.baseExceptionModel.BaseErrorModel;
import com.microservice.utils.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseErrorModel> handleBaseException(BaseException ex) {
        HttpStatus status = ex.getHttpStatus() != null ? ex.getHttpStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        return buildErrorResponse(ex.getErrorCode(), ex.getMessage(), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseErrorModel> handleGenericException(Exception ex) {
        return buildErrorResponse("INTERNAL_SERVER_ERROR", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<BaseErrorModel> buildErrorResponse(String errorCode, String errorMessage, HttpStatus status) {
        BaseErrorModel response = BaseErrorModel.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .errorDetails(DateUtil.formatDatTimeDefaultNow())
                .build();
        return ResponseEntity.status(status).body(response);
    }
}