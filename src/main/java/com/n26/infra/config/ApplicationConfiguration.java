package com.n26.infra.config;

import com.n26.domain.entity.statistics.StatisticsRepository;
import com.n26.domain.entity.transaction.TransactionsRepository;
import com.n26.domain.usecase.statistics.getstatistics.GetStatisticsUseCase;
import com.n26.domain.usecase.transaction.deletealltransactions.DeleteAllTransactionsUseCase;
import com.n26.domain.usecase.transaction.savetransaction.SaveTransactionUseCase;
import com.n26.infra.dataprovider.InMemoryTransactionsStatistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public SaveTransactionUseCase saveTransactionUseCase(TransactionsRepository transactionsRepository) {
        return new SaveTransactionUseCase(transactionsRepository);
    }

    @Bean
    public GetStatisticsUseCase getStatisticsUseCase(StatisticsRepository statisticsRepository) {
        return new GetStatisticsUseCase(statisticsRepository);
    }

    @Bean
    public DeleteAllTransactionsUseCase deleteAllTransactionsUseCase(TransactionsRepository transactionsRepository) {
        return new DeleteAllTransactionsUseCase(transactionsRepository);
    }

    @Bean
    public InMemoryTransactionsStatistics inMemoryTimeWindowRepository() {
        return new InMemoryTransactionsStatistics();
    }

}