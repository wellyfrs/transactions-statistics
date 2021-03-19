package com.n26.infra.dataprovider;

import com.n26.domain.entity.statistics.Statistics;
import com.n26.domain.entity.transaction.Transaction;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static org.junit.Assert.*;

public class InMemoryTransactionsStatisticsTest {

    private final InMemoryTransactionsStatistics inMemoryTransactionsStatistics = new InMemoryTransactionsStatistics();

    private static final long DURATION_IN_SECONDS = 60;
    private static final ZoneOffset ZONE_OFFSET = UTC;

    @Test
    public void transactionIsSavedIfNoHashMapCollision() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(1), now));

        Statistics expected = new Statistics(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                1L
        );

        Statistics result = inMemoryTransactionsStatistics.getStatisticsAfter(now.minusSeconds(DURATION_IN_SECONDS)).get();
        assertEquals(result, expected);
    }

    @Test
    public void transactionIsSavedIfHashMapCollision() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);

        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(2), now));
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(2), now));

        Statistics expected = new Statistics(
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                2L
        );

        Statistics result = inMemoryTransactionsStatistics.getStatisticsAfter(now.minusSeconds(DURATION_IN_SECONDS)).get();
        assertEquals(result, expected);
    }

    @Test
    public void emptyOptionalIsReturnIfNoStatistics() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);
        Optional<Statistics> result = inMemoryTransactionsStatistics.getStatisticsAfter(now.minusSeconds(DURATION_IN_SECONDS));
        assertFalse(result.isPresent());
    }

    @Test
    public void statisticsIsReturnedIfOnlyOneStatistics() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(2), now));
        Statistics result = inMemoryTransactionsStatistics.getStatisticsAfter(now.minusSeconds(DURATION_IN_SECONDS)).get();

        Statistics expected = new Statistics(
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                1L
        );

        assertEquals(result, expected);
    }

    @Test
    public void aggregatedStatisticsIsReturnedIfTwoOrMoreStatistics() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(2), now));
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(2), now));
        Statistics result = inMemoryTransactionsStatistics.getStatisticsAfter(now.minusSeconds(DURATION_IN_SECONDS)).get();

        Statistics expected = new Statistics(
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                BigDecimal.valueOf(2),
                2L
        );

        assertEquals(result, expected);
    }

    @Test
    public void allTransactionsAreRemoved() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(1), now));
        inMemoryTransactionsStatistics.removeAll();
        assertTrue(inMemoryTransactionsStatistics.isEmpty());
    }

    @Test
    public void allTransactionsBeforeTimeEpochAreRemoved() {
        OffsetDateTime now = OffsetDateTime.now(ZONE_OFFSET);
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(1), now.minusSeconds(20)));
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(2), now.minusSeconds(40)));
        inMemoryTransactionsStatistics.removeAllBefore(now.minusSeconds(30));
        Statistics result = inMemoryTransactionsStatistics.getStatisticsAfter(now.minusSeconds(DURATION_IN_SECONDS)).get();

        Statistics expected = new Statistics(
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(1),
                1L
        );

        assertEquals(result, expected);
    }

    @Test
    public void emptyVerificationShouldWorkProperly() {
        assertTrue(inMemoryTransactionsStatistics.isEmpty());
        inMemoryTransactionsStatistics.saveTransaction(new Transaction(new BigDecimal(1), OffsetDateTime.now(ZONE_OFFSET)));
        assertFalse(inMemoryTransactionsStatistics.isEmpty());
        inMemoryTransactionsStatistics.removeAll();
        assertTrue(inMemoryTransactionsStatistics.isEmpty());
    }
}