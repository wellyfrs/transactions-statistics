package com.n26.domain.usecase.statistics.getstatistics;

import com.n26.domain.entity.statistics.Statistics;
import com.n26.domain.entity.statistics.StatisticsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetStatisticsUseCaseTest {

    @Mock
    private StatisticsRepository statisticsRepository;

    @InjectMocks
    private GetStatisticsUseCase getStatisticsUseCase;

    @Test
    public void shouldReturnZeroStatisticsIfNoTransactions() {
        when(statisticsRepository.getStatisticsAfter(any())).thenReturn(Optional.empty());

        GetStatisticsResponseModel response = getStatisticsUseCase.getStatistics();

        GetStatisticsResponseModel expected = new GetStatisticsResponseModel(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                0L
        );

        assertEquals(response, expected);
    }

    @Test
    public void shouldReturnAggregatedStatisticsIfTransactions() {
        when(statisticsRepository.getStatisticsAfter(any())).thenReturn(Optional.of(new Statistics(
                BigDecimal.valueOf(6),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(2),
                3L
        )));

        GetStatisticsResponseModel response = getStatisticsUseCase.getStatistics();

        GetStatisticsResponseModel expected = new GetStatisticsResponseModel(
                BigDecimal.valueOf(6),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(3),
                BigDecimal.valueOf(2),
                3L
        );

        assertEquals(response, expected);
    }

}