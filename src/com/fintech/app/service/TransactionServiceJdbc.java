package com.fintech.app.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.fintech.app.config.DBConnection;
import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.enums.TransactionStatus;
import com.fintech.app.model.Account;
import com.fintech.app.model.Transaction;
import com.fintech.app.model.TransactionResult;
import com.fintech.app.util.IdGenerator;
import com.fintech.app.repository.interfaces.AccountRepositoryInterface;
import com.fintech.app.repository.interfaces.TransactionRepositoryInterface;

public class TransactionServiceJdbc {

	private final AccountRepositoryInterface accountRepository;
	private final TransactionRepositoryInterface transactionRepository;
    private final CurrencyService currencyService;

    public TransactionServiceJdbc(AccountRepositoryInterface accountRepository,
            TransactionRepositoryInterface transactionRepository,
            CurrencyService currencyService) {
    	this.accountRepository = accountRepository;
    	this.transactionRepository = transactionRepository;
    	this.currencyService = currencyService;
    }

    public TransactionResult processTransaction(TransactionRequestDTO request) {
        Account source = accountRepository.findById(request.getSourceAccountId());
        Account destination = accountRepository.findById(request.getDestinationAccountId());

        double creditedAmount = currencyService.convert(
                request.getSourceCurrency(),
                request.getTargetCurrency(),
                request.getAmount()
        );

        Connection connection = null;

        try {
            connection = DBConnection.getConnection();
            connection.setAutoCommit(false);

            source.debit(request.getAmount());
            destination.credit(creditedAmount);

            accountRepository.update(source, connection);
            accountRepository.update(destination, connection);
            
           // if (request.getAmount() == 9999.0) {
           //     throw new RuntimeException("Forced failure for rollback testing");
           // }

            Transaction transaction = new Transaction(
                    IdGenerator.generateId("TXN"),
                    request.getSourceAccountId(),
                    request.getDestinationAccountId(),
                    request.getAmount(),
                    request.getSourceCurrency(),
                    request.getTargetCurrency(),
                    request.getTransactionType(),
                    TransactionStatus.SUCCESS,
                    LocalDateTime.now()
            );

            transactionRepository.save(transaction, connection);

            connection.commit();

            return new TransactionResult(transaction, creditedAmount);

        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }
            throw new RuntimeException("Database transaction failed: " + e.getMessage(), e);

        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public double getCreditedAmount(TransactionRequestDTO request) {
        return currencyService.convert(
                request.getSourceCurrency(),
                request.getTargetCurrency(),
                request.getAmount()
        );
    }
}