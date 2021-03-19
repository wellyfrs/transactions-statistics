package com.n26.domain.usecase.transaction.savetransaction.exception;

import java.time.OffsetDateTime;

public class TransactionAfterOffsetException extends Exception {

    public TransactionAfterOffsetException(OffsetDateTime timestamp, OffsetDateTime now) {
        super(String.format("Transaction at %s after %s", timestamp, now));
    }

}