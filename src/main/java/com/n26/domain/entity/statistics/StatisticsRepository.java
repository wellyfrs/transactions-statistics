package com.n26.domain.entity.statistics;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface StatisticsRepository {

    Optional<Statistics> getStatisticsAfter(OffsetDateTime offsetDateTime);

}