package com.n26.domain.entity.statistics;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class StatisticsTest {

    @Test
    public void shouldAggregateProperly() {
        Statistics a = new Statistics(BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1), BigDecimal.valueOf(1), 1L);
        Statistics b = new Statistics(BigDecimal.valueOf(3), BigDecimal.valueOf(3), BigDecimal.valueOf(2), BigDecimal.valueOf(2), 1L);
        Statistics expected = new Statistics(BigDecimal.valueOf(4), BigDecimal.valueOf(2), BigDecimal.valueOf(2), BigDecimal.valueOf(1), 2L);
        Statistics result = a.merge(b);
        assertEquals(result, expected);
    }

}