package com.n26.domain.entity.transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Transaction {

    private final BigDecimal amount;
    private final OffsetDateTime timestamp;

    public Transaction(BigDecimal amount, OffsetDateTime timestamp) {
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
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }

}