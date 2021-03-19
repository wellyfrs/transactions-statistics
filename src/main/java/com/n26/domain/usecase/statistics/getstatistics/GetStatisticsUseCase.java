package com.n26.domain.usecase.statistics.getstatistics;

import com.n26.domain.entity.statistics.StatisticsRepository;

import java.math.BigDecimal;

import static com.n26.domain.usecase.TimeWindowUtils.getOnset;
import static com.n26.domain.usecase.TimeWindowUtils.now;

public class GetStatisticsUseCase {

    private final StatisticsRepository statisticsRepository;

    public GetStatisticsUseCase(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public GetStatisticsResponseModel getStatistics() {
        return statisticsRepository.getStatisticsAfter(getOnset(now()))
                .map(GetStatisticsResponseModel::factory)
                .orElse(new GetStatisticsResponseModel(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0L));
    }

}