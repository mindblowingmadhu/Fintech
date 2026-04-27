package com.fintech.app.repository.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.fintech.app.model.Account;

public interface AccountRepositoryInterface {

    void save(Account account);

    Account findById(String accountId);

    void update(Account account);

    default void save(Account account, Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Connection-based save not supported");
    }

    default void update(Account account, Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Connection-based update not supported");
    }
}