package com.n26.domain.usecase;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static java.time.ZoneOffset.UTC;

public final class TimeWindowUtils {

    private TimeWindowUtils() {}

    public static final long DURATION_IN_SECONDS = 60;
    public static final ZoneOffset ZONE_OFFSET = UTC;

    public static OffsetDateTime now() {
        return OffsetDateTime.now(ZONE_OFFSET);
    }

    public static OffsetDateTime getOnset(OffsetDateTime offset) {
        return offset.minusSeconds(DURATION_IN_SECONDS);
    }

}