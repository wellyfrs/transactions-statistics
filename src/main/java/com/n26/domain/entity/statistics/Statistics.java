package com.n26.domain.entity.statistics;

import com.n26.domain.entity.transaction.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Statistics {

    private final BigDecimal sum;
    private final BigDecimal avg;
    private final BigDecimal max;
    private final BigDecimal min;
    private final Long count;

    public Statistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, Long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public Statistics(Transaction transaction) {
        this.sum = transaction.getAmount();
        this.avg = transaction.getAmount();
        this.max = transaction.getAmount();
        this.min = transaction.getAmount();
        this.count = 1L;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public Long getCount() {
        return count;
    }

    public Statistics merge(Statistics statistics) {
        return new Statistics(
                sum.add(statistics.getSum()),
                sum.add(statistics.getSum()).divide(BigDecimal.valueOf(count + statistics.getCount()), RoundingMode.HALF_UP),
                max.compareTo(statistics.getMax()) > 0 ? max : statistics.getMax(),
                min.compareTo(statistics.getMin()) < 0 ? min : statistics.getMin(),
                count + statistics.getCount()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(sum, that.sum) && Objects.equals(avg, that.avg) && Objects.equals(max, that.max) && Objects.equals(min, that.min) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }

}