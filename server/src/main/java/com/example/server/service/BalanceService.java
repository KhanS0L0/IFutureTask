package com.example.server.service;

/**
 * Интерфейс сервиса для работы с банковским счётом содержит два метода
 **/
public interface BalanceService {
    /**
     *  Получение баланса
     *
     *  @param id идентификатор банковского счёта
     *  @return сумма денег на банковском счёте
     */
    Long getBalance(Long id);

    /**
     *  Изменение баланса на определённое значение
     *
     *  @param id идентификатор банковского счёта
     *  @param amount сумма денег, которую нужно добавить к банковскому счёту
     */
    Long changeBalance(Long id, Long amount);
}

