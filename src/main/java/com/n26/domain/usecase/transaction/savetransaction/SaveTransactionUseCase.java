package com.n26.domain.usecase.transaction.savetransaction;

import com.n26.domain.entity.transaction.Transaction;
import com.n26.domain.entity.transaction.TransactionsRepository;
import com.n26.domain.usecase.TimeWindowUtils;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionAfterOffsetException;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionBeforeOnsetException;

import java.time.OffsetDateTime;

public class SaveTransactionUseCase {

    private final TransactionsRepository transactionsRepository;

    public SaveTransactionUseCase(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public void saveTransaction(SaveTransactionRequestModel request)
            throws TransactionBeforeOnsetException, TransactionAfterOffsetException {
        OffsetDateTime now = TimeWindowUtils.now();
        OffsetDateTime onset = TimeWindowUtils.getOnset(now);

        validateRequestModel(request, now, onset);

        Transaction transaction = new Transaction(request.getAmount(), request.getTimestamp());

        transactionsRepository.saveTransaction(transaction);
        transactionsRepository.removeAllBefore(onset);
    }

    private void validateRequestModel(SaveTransactionRequestModel request, OffsetDateTime now, OffsetDateTime onset)
            throws TransactionBeforeOnsetException, TransactionAfterOffsetException {
        if (request.getTimestamp().isBefore(onset)) {
            throw new TransactionBeforeOnsetException(request.getTimestamp(), onset);
        }

        if (request.getTimestamp().isAfter(now)) {
            throw new TransactionAfterOffsetException(request.getTimestamp(), now);
        }
    }

}