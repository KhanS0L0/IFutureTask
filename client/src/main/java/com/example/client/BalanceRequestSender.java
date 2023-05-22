package com.example.client;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Класс эмулирующий http-запросы к BalanceService.
 * author KhanSOLO
 */
public class BalanceRequestSender {

    /**
     * Шаблон для отправки http-запросов
     * {@link org.springframework.web.client.RestTemplate}
     */
    private final RestTemplate restTemplate;

    public BalanceRequestSender() {
        restTemplate = new RestTemplate();
    }

    /**
     *  Получение баланса
     *
     *  @param id идентификатор банковского счёта
     *  @return сумма денег на банковском счёте
     */
    public Long getBalance(Long id){
        return restTemplate.getForObject("http://localhost:8090/balance/{id}", Long.class, id);
    }

    /**
     *  Изменение баланса на определённое значение
     *
     *  @param id идентификатор банковского счёта
     *  @param amount сумма денег, которую нужно добавить к банковскому счёту
     */
    public void changeBalance(Long id, Long amount){
        HttpEntity<Long> requestBody = new HttpEntity<>(amount);
        restTemplate.exchange("http://localhost:8090/balance/{id}", HttpMethod.PUT, requestBody, Void.class, id);
    }

}
