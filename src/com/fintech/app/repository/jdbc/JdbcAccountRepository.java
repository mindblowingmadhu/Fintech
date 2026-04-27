package com.fintech.app.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fintech.app.config.DBConnection;
import com.fintech.app.enums.AccountStatus;
import com.fintech.app.model.Account;
import com.fintech.app.repository.interfaces.AccountRepositoryInterface;

public class JdbcAccountRepository implements AccountRepositoryInterface  {

    public void save(Account account) {
        String sql = "INSERT INTO accounts (account_id, user_id, balance, currency, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection()) {
            save(account, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void save(Account account, Connection connection) throws SQLException {
        String sql = "INSERT INTO accounts (account_id, user_id, balance, currency, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, account.getAccountId());
            pstmt.setString(2, account.getUserId());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setString(4, account.getCurrency());
            pstmt.setString(5, account.getStatus().name());

            pstmt.executeUpdate();
        }
    }

    public Account findById(String accountId) {
        String sql = "SELECT * FROM accounts WHERE account_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, accountId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Account(
                        rs.getString("account_id"),
                        rs.getString("user_id"),
                        rs.getDouble("balance"),
                        rs.getString("currency"),
                        AccountStatus.valueOf(rs.getString("status"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void update(Account account) {
        try (Connection connection = DBConnection.getConnection()) {
            update(account, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Account account, Connection connection) throws SQLException {
        String sql = "UPDATE accounts SET balance = ?, currency = ?, status = ? WHERE account_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, account.getBalance());
            pstmt.setString(2, account.getCurrency());
            pstmt.setString(3, account.getStatus().name());
            pstmt.setString(4, account.getAccountId());

            pstmt.executeUpdate();
        }
    }
}