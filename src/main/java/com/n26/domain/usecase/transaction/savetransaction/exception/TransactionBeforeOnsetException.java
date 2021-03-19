package com.n26.domain.usecase.transaction.savetransaction.exception;

import java.time.OffsetDateTime;

public class TransactionBeforeOnsetException extends Exception {

    public TransactionBeforeOnsetException(OffsetDateTime timestamp, OffsetDateTime onset) {
        super(String.format("Transaction at %s before %s", timestamp, onset));
    }

}