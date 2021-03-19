package com.n26.infra.entrypoint.transaction;

import com.n26.domain.usecase.transaction.deletealltransactions.DeleteAllTransactionsUseCase;
import com.n26.domain.usecase.transaction.savetransaction.SaveTransactionRequestModel;
import com.n26.domain.usecase.transaction.savetransaction.SaveTransactionUseCase;
import com.n26.domain.usecase.transaction.savetransaction.exception.TransactionBeforeOnsetException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static java.time.ZoneOffset.UTC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {TransactionController.class})
public class TransactionControllerTest {

    private static final String URI = "/transactions";
    private static final ZoneOffset ZONE_OFFSET = UTC;

    @MockBean
    private SaveTransactionUseCase saveTransactionUseCase;

    @MockBean
    private DeleteAllTransactionsUseCase deleteAllTransactionsUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnCreatedIfTransactionIsSaved() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                String.format("{\"amount\": \"12.3343\", \"timestamp\": \"%s\"}", OffsetDateTime.now(ZONE_OFFSET))));
        resultActions.andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnNoContentIfTransactionIsOlderThan60Seconds() throws Exception {
        doThrow(TransactionBeforeOnsetException.class).when(saveTransactionUseCase).saveTransaction(any());

        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                "{\"amount\": \"12.3343\", \"timestamp\": \"2018-07-17T09:59:51.312Z\"}"));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnBadRequestIfRequestBodyIsInvalid() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                "{\"timestamp\": \"2018-07-17T09:59:51.312Z\"}"));
        resultActions.andExpect(status().isBadRequest());

        resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                "{\"amount\": \"12.3343\"}"));
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnUnprocessableEntityIfRequestBodyIsNotParsable() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                "{\"amount\": \"12.3343\", \"timestamp\": \"2018-07-17\"}"));
        resultActions.andExpect(status().isUnprocessableEntity());

        resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                "{\"amount\": \"12,3343\", \"timestamp\": \"2018-07-17T09:59:51.312Z\"}"));
        resultActions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void ShouldReturnBadRequestIfRequestBodyIsEmpty() throws Exception {
        ResultActions resultActions = mockMvc.perform(post(URI).contentType(MediaType.APPLICATION_JSON_UTF8).content(""));
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnNoContentOnDeleteAllTransactions() throws Exception {
        ResultActions resultActions = mockMvc.perform(delete(URI).contentType(MediaType.APPLICATION_JSON_UTF8));
        resultActions.andExpect(status().isNoContent());
    }

}