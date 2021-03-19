package com.n26.domain.usecase.transaction.deletealltransactions;

import com.n26.domain.entity.transaction.TransactionsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class DeleteAllTransactionsUseCaseTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private DeleteAllTransactionsUseCase deleteAllTransactionsUseCase;

    @Test
    public void validTransactionIsSaved() {
        deleteAllTransactionsUseCase.deleteAllTransactions();
        verify(transactionsRepository, times(1)).removeAll();
    }

}