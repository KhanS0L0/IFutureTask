package com.example.server.service.impl;

import com.example.server.error.ErrorDescription;
import com.example.server.repository.BalanceRepository;
import com.example.server.service.BalanceService;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Класс реализующий логику работы сервиса для работы с банковским счётом.
 * @author KhanSOLO
 */
@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    /**
     *  Получение баланса
     *
     *  @param id идентификатор банковского счёта
     *  @return сумма денег на банковском счёте
     */
    @Override
    @Cacheable(value = "balanceCache", key = "#id", sync = true)
    public Long getBalance(Long id) {
        var balance = balanceRepository.findById(id)
                .orElseThrow(ErrorDescription.BALANCE_NOT_FOUND::exception);
        return balance.getAmount();
    }

    /**
     *  Изменение баланса на определённое значение
     *
     *  @param id идентификатор банковского счёта
     *  @param amount сумма денег, которую нужно добавить к банковскому счёту
     */
    @Override
    @Transactional
    @CachePut(value = "balanceCache", key = "#p0")
    public Long changeBalance(Long id, Long amount) {
        var balance = balanceRepository.findById(id)
                .orElseThrow(ErrorDescription.BALANCE_NOT_FOUND::exception);
        long sum = amount + balance.getAmount();
        if (sum < balance.getAmount()) throw ErrorDescription.CHANGE_BALANCE_NOT_VALID.exception();
        balance.setAmount(sum);
        return balance.getAmount();
    }
}
