package com.example.client;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Класс эмулирующий многопоточное обращение к BalanceService.
 * Данный класс отправляет http-запросы к сервису подсчета баланса счета в несколько потоков
 * author KhanSOLO
 */
public class Main {

    /**
     * threadCount - количество клиентских потоков (>= 1)
     */
    public static final int threadCount = 10;

    /**
     * readQuota   - доля запросов getBalance (>= 0)
     */
    public static final double readQuota = 0.1;


    /**
     * writeQuota  - доля запросов changeBalance (>= 0)
     */
    public static final double writeQuota = 0.3;

    /**
     * readIdList  - список идентификаторов для getBalance
     */
    public static final List<Long> readIdList = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

    /**
     * writeIdList - список идентификаторов для changeBalance
     */
    public static final List<Long> writeIdList = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);

    /**
     * Инициализация потоков и запуск в многопоточной среде
     */
    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>(threadCount);
        for(int i = 0; i < threadCount; i++){threads.add(createThread());}
        threads.forEach(Thread::start);
    }

    /**
     * Утилитарный метод для получения случайного элемента списка
     * @param list - список объектов из которого нужно получить случайный элемент
     * @return случайный элемент
     */
    public static Object getRandomElement(List<?> list) {
        int index = ThreadLocalRandom.current().nextInt(list.size());
        return list.get(index);
    }

    /**
     * Утилитарный метод для создания потока.
     * Внутри потока бесконечный цикл будет отправлять http-запросы в "случайном" порядке
     * @return {@link java.lang.Thread}
     */
    public static Thread createThread(){
        var sender = new BalanceRequestSender();
        var counter = new BalanceRequestMultithreadingCounter();
        return new Thread(() -> {
            while (true){
                double readProbability = readQuota / (readQuota + writeQuota);
                if (ThreadLocalRandom.current().nextDouble() < readProbability){
                    var randomId = (Long) getRandomElement(readIdList);
                    sender.getBalance(randomId);
                    counter.incrementGetBalanceCounter(randomId);
                } else {
                    var randomId = (Long) getRandomElement(writeIdList);
                    sender.changeBalance(randomId, 1L);
                    counter.incrementChangeBalanceCounter(randomId);
                }
            }
        });
    }
}