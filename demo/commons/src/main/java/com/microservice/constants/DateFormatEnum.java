package com.microservice.constants;

import lombok.Getter;

@Getter
public enum DateFormatEnum {
    DATE_TIME_DEFAULT("dd/MM/yyyy HH:mm:ss"),
    DATE_ONLY_DEFAULT("dd/MM/yyyy"),
    DATE_STR_SIMPLE("yyyyMMdd"),
    DATE_ISO("yyyy-MM-dd");

    private final String format;

    DateFormatEnum(String format) {
        this.format = format;
    }
}
