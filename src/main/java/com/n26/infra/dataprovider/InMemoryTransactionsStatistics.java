package com.n26.infra.dataprovider;

import com.n26.domain.entity.statistics.Statistics;
import com.n26.domain.entity.statistics.StatisticsRepository;
import com.n26.domain.entity.transaction.Transaction;
import com.n26.domain.entity.transaction.TransactionsRepository;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTransactionsStatistics implements TransactionsRepository, StatisticsRepository {

    private final ConcurrentHashMap<Long, Statistics> statisticsByEpochSecond = new ConcurrentHashMap<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        statisticsByEpochSecond.merge(
                transaction.getTimestamp().toEpochSecond(),
                new Statistics(transaction),
                Statistics::merge
        );
    }

    @Override
    public Optional<Statistics> getStatisticsAfter(OffsetDateTime offsetDateTime) {
        return statisticsByEpochSecond.entrySet()
                .stream()
                .filter(i -> i.getKey() > offsetDateTime.toEpochSecond())
                .map(Map.Entry::getValue)
                .reduce(Statistics::merge);
    }

    @Override
    public void removeAllBefore(OffsetDateTime offsetDateTime) {
        statisticsByEpochSecond.keySet()
                .stream()
                .filter(i -> i < offsetDateTime.toEpochSecond())
                .forEach(statisticsByEpochSecond::remove);
    }

    @Override
    public void removeAll() {
        statisticsByEpochSecond.clear();
    }

    public boolean isEmpty() {
        return statisticsByEpochSecond.isEmpty();
    }

}