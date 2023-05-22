package com.example.server.configuration;

import com.example.server.error.ApplicationError;
import com.example.server.error.ApplicationException;
import com.example.server.error.ErrorDescription;
import com.example.server.error.ValidationError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * {@link ControllerAdvice}, обрабатывающий возникающие исключения.
 *
 * @author KhanSOLO
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработка бизнес исключений.
     *
     * @param ex исключение.
     * @param response ответ, отдаваемый пользователю.
     * @return модель исключения отдаваемая клиенту.
     */
    @ResponseBody
    @ExceptionHandler(ApplicationException.class)
    public ApplicationError handleApplicationException(ApplicationException ex, HttpServletResponse response) {
        response.setStatus(ex.getError().getHttpStatus().value());
        return ex.getError();
    }

    /**
     * Обработка исключений валидации.
     *
     * @param ex исключение.
     * @return модель исключения отдаваемая клиенту.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApplicationError handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationError> validationErrorList = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(
                        it -> validationErrorList.add(
                                new ValidationError(Objects.requireNonNull(it.getDefaultMessage()), it.getField())
                        )
                );
        ApplicationError error = ErrorDescription.USER_DATA_NOT_VALID.createApplicationError();
        error.setData(validationErrorList);
        return error;
    }

    /**
     * Обработка исключений чтения данных пользователя.
     *
     * @param ex исключение.
     * @return модель исключения отдаваемая клиенту.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ApplicationError handleMessageNotReadableException(HttpMessageNotReadableException ex){
        return ErrorDescription.USER_DATA_NOT_VALID.createApplicationError();
    }

    /**
     * Обработка исключений чтения данных пользователя.
     *
     * @param ex исключение.
     * @return модель исключения отдаваемая клиенту.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ApplicationError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        return ErrorDescription.USER_DATA_NOT_VALID.createApplicationError();
    }

    /**
     * Обработка всех исключений, для которых не настроен отдельный маппинг.
     *
     * @param ex исключение.
     * @return модель исключения отдаваемая клиенту.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApplicationError handleCommonException(Exception ex){
        ApplicationError error = ErrorDescription.UNKNOWN_ERROR.createApplicationError();
        if (ex.getCause() != null) {
            error.setCause(ex.getCause().getMessage());
        }
        return error;
    }

}
