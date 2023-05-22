package com.example.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Модель запроса на изменение баланса
 * @author KhanSOLO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ChangeBalanceDto {

    private Long amount;
}
