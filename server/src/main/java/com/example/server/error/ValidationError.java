package com.example.server.error;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Модель отдаваемая клиенту при исключениях валидации.
 * Хранит в себе какое поле не прошло валидацию и сообщение подсказку
 * @author KhanSOLO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {

    private String message;

    private String field;

}
