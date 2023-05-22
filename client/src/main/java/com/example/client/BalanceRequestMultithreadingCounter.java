package com.example.client;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * Класс подсчитывающий количество запросов выполняемых в разных потоках
 * author KhanSOLO
 */
@Slf4j
public class BalanceRequestMultithreadingCounter {

    /**
     * Хэш-карта, хранящая количество {@link BalanceRequestSender#getBalance(Long)} запросов по их идентификатором
     * В качестве ключа выступает идентификатор счета, значение - количество обращений к этому счету
     * {@link java.util.concurrent.ConcurrentHashMap}
     */
    private final Map<Long, LongAdder> getBalanceCounters;

    /**
     * Хэш-карта, хранящая количество {@link BalanceRequestSender#changeBalance(Long, Long)} } запросов по их идентификатором
     * В качестве ключа выступает идентификатор счета, значение - количество обращений к этому счету
     * {@link java.util.concurrent.ConcurrentHashMap}
     */
    private final Map<Long, LongAdder> changeBalanceCounter;

    /**
     * Хэш-карта, хранящая общее количество {@link BalanceRequestSender#getBalance(Long)} запросов между потоками
     * {@link java.util.concurrent.atomic.LongAdder}
     */
    private final LongAdder totalGetBalanceCounter;

    /**
     * Хэш-карта, хранящая общее количество {@link BalanceRequestSender#changeBalance(Long, Long)} запросов между потоками
     * {@link java.util.concurrent.atomic.LongAdder}
     */
    private final LongAdder totalChangeBalanceCounter;

    /**
     * Количество секунд между выводом в консоль информации по запросам
     */
    private static final int LOG_INTERVAL_SECONDS = 1;

    public BalanceRequestMultithreadingCounter() {
        this.getBalanceCounters = new ConcurrentHashMap<>();
        this.changeBalanceCounter = new ConcurrentHashMap<>();
        this.totalGetBalanceCounter = new LongAdder();
        this.totalChangeBalanceCounter = new LongAdder();

        //Количество запросов getBalance, changeBalance и их сумма в единицу времени. Результат записывать в лог.
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::logRequestCounters, LOG_INTERVAL_SECONDS, LOG_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * потоко-безопасное увеличение счетчика выполнения запроса {@link BalanceRequestSender#getBalance(Long)}
     * @param id - уникальный идентификатор счета
     */
    public void incrementGetBalanceCounter(Long id) {
        getBalanceCounters.computeIfAbsent(id, k -> new LongAdder()).increment();
        totalGetBalanceCounter.increment();
    }

    /**
     * потоко-безопасное увеличение счетчика выполнения запроса {@link BalanceRequestSender#changeBalance(Long, Long)}
     * @param id - уникальный идентификатор счета
     */
    public void incrementChangeBalanceCounter(Long id) {
        changeBalanceCounter.computeIfAbsent(id, k -> new LongAdder()).increment();
        totalChangeBalanceCounter.increment();
    }

    /**
     * Вывод в консоль информации о количестве запросов getBalance, changeBalance и их сумме в момент времени
     */
    private void logRequestCounters() {
        log.info("Request counters (last {} seconds):", LOG_INTERVAL_SECONDS);
        log.info("Total getBalance requests: {}", totalGetBalanceCounter.sum());
        log.info("Total changeBalance requests: {}", totalChangeBalanceCounter.sum());

        getBalanceCounters.forEach((id, counter) -> log.info("getBalance requests for {}: {}", id, counter.sum()));
        changeBalanceCounter.forEach((id, counter) -> log.info("changeBalance requests for {}: {}", id, counter.sum()));

        // Обнуляем счетчики запросов
        getBalanceCounters.clear();
        changeBalanceCounter.clear();
        totalGetBalanceCounter.reset();
        totalChangeBalanceCounter.reset();
    }
}
