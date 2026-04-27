package com.fintech.app.repository.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fintech.app.model.Transaction;

public interface TransactionRepositoryInterface {

    void save(Transaction transaction);

    default void save(Transaction transaction, Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Connection-based save not supported");
    }

    default List<Transaction> findAll() {
        throw new UnsupportedOperationException("findAll not supported");
    }
}