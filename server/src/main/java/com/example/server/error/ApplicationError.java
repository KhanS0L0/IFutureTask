package com.example.server.error;

import lombok.Data;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Модель исключения отдаваемая клиенту.
 * @author KhanSOLO
 */
@Data
public class ApplicationError {

    /**
     * Уникальный код исключения.
     */
    private String code;

    /**
     * Сообщение об исключении.
     */
    private String message;

    /**
     * Причина исключения.
     */
    private String cause;

    /**
     * Данные об полях непрошедшие валидацию.
     * {@link ValidationError}
     */
    private List<ValidationError> data;

    /**
     * HTTP статус исключения.
     */
    private HttpStatus httpStatus;

    public ApplicationError() {
    }

    public ApplicationError(String code, String message, String cause, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.cause = cause;
        this.httpStatus = httpStatus;
    }
}
