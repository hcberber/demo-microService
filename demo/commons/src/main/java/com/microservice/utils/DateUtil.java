package com.microservice.utils;


import com.microservice.constants.DateFormatEnum;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateUtil {

    public static String formatDatTimeDefaultNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormatEnum.DATE_TIME_DEFAULT.getFormat()));
    }

    public static String formatDateTimeDefault(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DateFormatEnum.DATE_TIME_DEFAULT.getFormat()));
    }

    public static String formatDateDefaultNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormatEnum.DATE_ONLY_DEFAULT.getFormat()));
    }

    public static String formatDateDefault(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DateFormatEnum.DATE_ONLY_DEFAULT.getFormat()));
    }


    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }


    public static LocalDateTime toLocalDateTime(LocalDateTime dateTime) {
        return dateTime;
    }


    public static LocalDateTime fromLocalDate(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }


    public static LocalDate strSimpleToLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) return null;
        try {
            return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DateFormatEnum.DATE_STR_SIMPLE.getFormat()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid Date Format : " + dateStr, e);
        }
    }
}
