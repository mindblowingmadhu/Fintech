package com.fintech.app.repository;

import java.util.ArrayList;
import java.util.List;

import com.fintech.app.model.Transaction;
import com.fintech.app.repository.interfaces.TransactionRepositoryInterface;

public class TransactionRepository implements TransactionRepositoryInterface  {
    private final List<Transaction> transactionStore = new ArrayList<>();

    public void save(Transaction transaction) {
        transactionStore.add(transaction);
    }

    public List<Transaction> findAll() {
        return transactionStore;
    }
}