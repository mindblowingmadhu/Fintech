package com.fintech.app.main;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.enums.TransactionType;
import com.fintech.app.model.TransactionResult;
import com.fintech.app.repository.jdbc.JdbcAccountRepository;
import com.fintech.app.repository.jdbc.JdbcTransactionRepository;
import com.fintech.app.service.CurrencyService;
import com.fintech.app.service.TransactionService;

public class TransactionRiskTest {

    public static void main(String[] args) {

        JdbcAccountRepository accountRepo = new JdbcAccountRepository();
        JdbcTransactionRepository transactionRepo = new JdbcTransactionRepository();
        CurrencyService currencyService = new CurrencyService();

        TransactionService transactionService =
                new TransactionService(accountRepo, transactionRepo, currencyService);

        TransactionRequestDTO request = new TransactionRequestDTO(
                "A101",
                "A102",
                80000,
                "INR",
                "USD",
                TransactionType.CROSS_BORDER
        );

        TransactionResult result = transactionService.processTransaction(request);

        System.out.println("Transaction Status: " + result.getTransaction().getStatus());
        System.out.println("Credited Amount: " + result.getCreditedAmount());
    }
}