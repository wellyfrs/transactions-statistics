package com.n26.domain.usecase.statistics.getstatistics;

import com.n26.domain.entity.statistics.Statistics;

import java.math.BigDecimal;
import java.util.Objects;

public class GetStatisticsResponseModel {

    private final BigDecimal sum;
    private final BigDecimal avg;
    private final BigDecimal max;
    private final BigDecimal min;
    private final Long count;

    public static GetStatisticsResponseModel factory(Statistics statistics) {
        return new GetStatisticsResponseModel(
                statistics.getSum(),
                statistics.getAvg(),
                statistics.getMax(),
                statistics.getMin(),
                statistics.getCount()
        );
    }

    public GetStatisticsResponseModel(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, Long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetStatisticsResponseModel that = (GetStatisticsResponseModel) o;
        return Objects.equals(sum, that.sum) && Objects.equals(avg, that.avg) && Objects.equals(max, that.max) && Objects.equals(min, that.min) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }

    @Override
    public String toString() {
        return "GetStatisticsResponseModel{" +
                "sum=" + sum +
                ", avg=" + avg +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }

}