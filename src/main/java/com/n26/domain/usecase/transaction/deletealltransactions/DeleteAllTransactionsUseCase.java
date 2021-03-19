package com.n26.domain.usecase.transaction.deletealltransactions;

import com.n26.domain.entity.transaction.TransactionsRepository;

public class DeleteAllTransactionsUseCase {

    private final TransactionsRepository transactionsRepository;

    public DeleteAllTransactionsUseCase(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public void deleteAllTransactions() {
        transactionsRepository.removeAll();
    }

}