package com.fintech.app.model;

public class TransactionResult {

    private Transaction transaction;
    private double creditedAmount;

    public TransactionResult() {
    }

    public TransactionResult(Transaction transaction, double creditedAmount) {
        this.transaction = transaction;
        this.creditedAmount = creditedAmount;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public double getCreditedAmount() {
        return creditedAmount;
    }

    public void setCreditedAmount(double creditedAmount) {
        this.creditedAmount = creditedAmount;
    }
}