package com.fintech.app.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.fintech.app.config.DBConnection;
import com.fintech.app.model.Transaction;
import com.fintech.app.repository.interfaces.TransactionRepositoryInterface;

public class JdbcTransactionRepository implements TransactionRepositoryInterface {

    public void save(Transaction transaction) {
        try (Connection connection = DBConnection.getConnection()) {
            save(transaction, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Transaction transaction, Connection connection) throws SQLException {
        String sql = "INSERT INTO transactions (transaction_id, source_account_id, destination_account_id, amount, source_currency, target_currency, transaction_type, status, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, transaction.getTransactionId());
            pstmt.setString(2, transaction.getSourceAccountId());
            pstmt.setString(3, transaction.getDestinationAccountId());
            pstmt.setDouble(4, transaction.getAmount());
            pstmt.setString(5, transaction.getSourceCurrency());
            pstmt.setString(6, transaction.getTargetCurrency());
            pstmt.setString(7, transaction.getTransactionType().name());
            pstmt.setString(8, transaction.getStatus().name());
            pstmt.setTimestamp(9, Timestamp.valueOf(transaction.getTimestamp()));

            pstmt.executeUpdate();
        }
    }
}