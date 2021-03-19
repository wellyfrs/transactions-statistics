package com.n26.infra.entrypoint.transaction;

import com.n26.domain.usecase.transaction.deletealltransactions.DeleteAllTransactionsUseCase;
import com.n26.domain.usecase.transaction.savetransaction.SaveTransactionRequestModel;
import com.n26.domain.usecase.transaction.savetransaction.SaveTransactionUseCase;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionAfterOffsetException;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionBeforeOnsetException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping(path = "/transactions")
@AllArgsConstructor
public class TransactionController {

    private final SaveTransactionUseCase saveTransactionUseCase;
    private final DeleteAllTransactionsUseCase deleteAllTransactionsUseCase;

    @PostMapping
    public ResponseEntity<String> saveTransaction(@Valid @RequestBody SaveTransactionRequest request) {
        try {
            SaveTransactionRequestModel requestModel = new SaveTransactionRequestModel(
                    new BigDecimal(request.getAmount()).setScale(2, RoundingMode.HALF_UP),
                    OffsetDateTime.parse(request.getTimestamp())
            );

            saveTransactionUseCase.saveTransaction(requestModel);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (TransactionBeforeOnsetException ex) {
            return ResponseEntity.noContent().build();
        } catch (DateTimeParseException | NumberFormatException | TransactionAfterOffsetException ex) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllTransactions() {
        deleteAllTransactionsUseCase.deleteAllTransactions();
        return ResponseEntity.noContent().build();
    }

}