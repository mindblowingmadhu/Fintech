package com.fintech.app.model;

import com.fintech.app.enums.AccountStatus;

public class Account {
    private String accountId;
    private String userId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public Account() {
    }

    public Account(String accountId, String userId, double balance, String currency, AccountStatus status) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
        this.status = status;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}