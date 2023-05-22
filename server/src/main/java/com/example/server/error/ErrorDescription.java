package com.example.server.error;

import org.springframework.http.HttpStatus;

/**
 * Ошибки приложения.
 * @author KhanSOLO
 */
public enum ErrorDescription {

    HANDLER_NOT_FOUND("SYSTEM_500", "Данный метод не поддерживается", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR("SYSTEM_500", "Неизвестная ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR),
    BALANCE_NOT_FOUND("BALANCE__001", "Баланс с заданным идентификатором не найден", HttpStatus.NOT_FOUND),
    CHANGE_BALANCE_NOT_VALID("BALANCE__002", "Сумма списания больше изначальной", HttpStatus.BAD_REQUEST),
    USER_DATA_NOT_VALID("VALIDATION__001", "Данные введенные пользователем не валидны", HttpStatus.BAD_REQUEST),
    ;

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorDescription(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ApplicationError createApplicationError(){
        return new ApplicationError(this.code, this.message, "", this.httpStatus);
    }

    public ApplicationException exception() {
        return new ApplicationException(createApplicationError());
    }

}
