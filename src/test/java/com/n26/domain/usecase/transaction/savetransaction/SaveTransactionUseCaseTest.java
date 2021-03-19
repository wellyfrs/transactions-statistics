package com.n26.domain.usecase.transaction.savetransaction;

import com.n26.domain.entity.transaction.TransactionsRepository;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionAfterOffsetException;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionBeforeOnsetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SaveTransactionUseCaseTest {

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private SaveTransactionUseCase saveTransactionService;

    @Test
    public void validTransactionShouldBeSaved() throws TransactionAfterOffsetException, TransactionBeforeOnsetException {
        SaveTransactionRequestModel requestModel = new SaveTransactionRequestModel(
                new BigDecimal(1), OffsetDateTime.now()
        );

        saveTransactionService.saveTransaction(requestModel);
        verify(transactionsRepository, times(1)).saveTransaction(any());
    }

    @Test(expected = TransactionBeforeOnsetException.class)
    public void transactionOlderThan60SecondsShouldBeNotSaved()
            throws TransactionAfterOffsetException, TransactionBeforeOnsetException {
        SaveTransactionRequestModel requestModel = new SaveTransactionRequestModel(
                new BigDecimal(1), OffsetDateTime.now().minusSeconds(60)
        );

        saveTransactionService.saveTransaction(requestModel);
    }

    @Test(expected = TransactionAfterOffsetException.class)
    public void futureTransactionShouldBeNotSaved()
            throws TransactionAfterOffsetException, TransactionBeforeOnsetException {
        SaveTransactionRequestModel requestModel = new SaveTransactionRequestModel(
                new BigDecimal(1), OffsetDateTime.now().plusSeconds(1)
        );

        saveTransactionService.saveTransaction(requestModel);
    }

}