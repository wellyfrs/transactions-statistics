package com.n26.domain.usecase.transaction.savetransaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public class SaveTransactionRequestModel {

    private final BigDecimal amount;

    private final OffsetDateTime timestamp;

    public SaveTransactionRequestModel(BigDecimal amount, OffsetDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveTransactionRequestModel that = (SaveTransactionRequestModel) o;
        return Objects.equals(amount, that.amount) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, timestamp);
    }

}