package com.example.server.common;

/**
 * Класс хранящий в себе неизменяемые константы url'ов к которым будет привязан контроллер.
 * author KhanSOLO
 */
public interface Endpoints {

    interface Balance{
        String BALANCE_BY_ID = "/balance/{id}";
    }
}
