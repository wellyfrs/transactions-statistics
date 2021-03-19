package com.n26.infra.entrypoint.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class GetStatisticsResponse {

    @JsonProperty("sum")
    private final String sum;

    @JsonProperty("avg")
    private final String avg;

    @JsonProperty("max")
    private final String max;

    @JsonProperty("min")
    private final String min;

    @JsonProperty("count")
    private final Long count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetStatisticsResponse that = (GetStatisticsResponse) o;
        return Objects.equals(sum, that.sum) && Objects.equals(avg, that.avg) && Objects.equals(max, that.max) && Objects.equals(min, that.min) && Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sum, avg, max, min, count);
    }

}