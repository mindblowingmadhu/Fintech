package com.fintech.app.dto;

import com.fintech.app.enums.TransactionStatus;

public class TransactionResponseDTO {

    private String transactionId;
    private TransactionStatus status;
    private String message;
    private double debitedAmount;
    private double creditedAmount;

    public TransactionResponseDTO() {
    }

    public TransactionResponseDTO(String transactionId, TransactionStatus status, String message,
                                  double debitedAmount, double creditedAmount) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
        this.debitedAmount = debitedAmount;
        this.creditedAmount = creditedAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public double getDebitedAmount() {
        return debitedAmount;
    }

    public void setDebitedAmount(double debitedAmount) {
        this.debitedAmount = debitedAmount;
    }

    public double getCreditedAmount() {
        return creditedAmount;
    }

    public void setCreditedAmount(double creditedAmount) {
        this.creditedAmount = creditedAmount;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}