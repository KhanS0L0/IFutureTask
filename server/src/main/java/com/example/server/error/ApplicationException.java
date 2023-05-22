package com.example.server.error;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Класс исключения для бекенд-части.
 * @author KhanSOLO
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException{

    /**
     * Модель исключения {@link ApplicationError}.
     */
    private ApplicationError error;

    /**
     * Исключение {@link Throwable}.
     */
    private Throwable throwable;

    /**
     * Сообщение об исключении.
     */
    private String errorMessage;


    public ApplicationException() {
    }

    public ApplicationException(Throwable throwable, String errorMessage) {
        this.throwable = throwable;
        this.errorMessage = errorMessage;
    }

    public ApplicationException(ApplicationError error) {
        this.error = error;
        this.errorMessage = error.getMessage();
    }

    public ApplicationException(ApplicationError error, Throwable throwable) {
        this.error = error;
        this.throwable = throwable;
        this.errorMessage = error.getMessage();
    }

}

