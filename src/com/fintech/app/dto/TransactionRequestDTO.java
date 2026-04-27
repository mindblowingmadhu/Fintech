package com.fintech.app.dto;

import com.fintech.app.enums.TransactionType;

public class TransactionRequestDTO {
    private String sourceAccountId;
    private String destinationAccountId;
    private double amount;
    private String sourceCurrency;
    private String targetCurrency;
    private TransactionType transactionType;

    public TransactionRequestDTO() {
    }

    public TransactionRequestDTO(
            String sourceAccountId,
            String destinationAccountId,
            double amount,
            String sourceCurrency,
            String targetCurrency,
            TransactionType transactionType) {

        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.transactionType = transactionType;
    }

    public String getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(String sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public String getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(String destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}