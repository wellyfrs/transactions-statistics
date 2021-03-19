package com.n26.infra.entrypoint.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class SaveTransactionRequest {

    @NotBlank
    @JsonProperty(value = "amount", required = true)
    private final String amount;

    @NotBlank
    @JsonProperty(value = "timestamp", required = true)
    private final String timestamp;

    public SaveTransactionRequest(String amount, String timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

}