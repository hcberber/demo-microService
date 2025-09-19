package com.microservice.exceptionManagement.baseExceptionModel;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseErrorModel {

    private String errorCode;

    private String errorMessage;

    private String errorDetails;
}