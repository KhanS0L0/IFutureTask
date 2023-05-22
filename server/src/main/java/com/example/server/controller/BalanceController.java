package com.example.server.controller;

import com.example.server.common.Endpoints;
import com.example.server.model.ChangeBalanceDto;
import com.example.server.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Контроллер обрабатывающий http-запросы к BalanceService.
 * @author KhanSOLO
 */
@RestController
@RequiredArgsConstructor
public class BalanceController {

    /**
     * {@link BalanceService}
     */
    private final BalanceService balanceService;

    /**
     * {@link BalanceService#getBalance(Long)}
     */
    @GetMapping(value = Endpoints.Balance.BALANCE_BY_ID)
    public Long getBalance(@PathVariable("id") Long id){
        return balanceService.getBalance(id);
    }

    /**
     * {@link BalanceService#changeBalance(Long, Long)}
     */
    @PutMapping(value = Endpoints.Balance.BALANCE_BY_ID)
    public void changeBalance(@PathVariable("id") Long balanceId, @RequestBody ChangeBalanceDto amount){
        balanceService.changeBalance(balanceId, amount.getAmount());
    }
}
