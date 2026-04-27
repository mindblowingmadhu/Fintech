package com.fintech.app.main;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.dto.TransactionResponseDTO;
import com.fintech.app.engine.TransactionEngineJdbc;
import com.fintech.app.enums.TransactionType;
import com.fintech.app.repository.jdbc.JdbcAccountRepository;
import com.fintech.app.repository.jdbc.JdbcTransactionRepository;
import com.fintech.app.service.CurrencyService;
import com.fintech.app.service.RiskService;
import com.fintech.app.service.TransactionServiceJdbc;
import com.fintech.app.service.ValidationServiceJdbc;

public class MainJdbc {

    public static void main(String[] args) {

        JdbcAccountRepository accountRepository = new JdbcAccountRepository();
        JdbcTransactionRepository transactionRepository = new JdbcTransactionRepository();

        ValidationServiceJdbc validationService = new ValidationServiceJdbc(accountRepository);
        RiskService riskService = new RiskService();
        CurrencyService currencyService = new CurrencyService();
        TransactionServiceJdbc transactionService =
                new TransactionServiceJdbc(accountRepository, transactionRepository, currencyService);

        TransactionEngineJdbc engine =
                new TransactionEngineJdbc(validationService, riskService, transactionService);

        TransactionRequestDTO request = new TransactionRequestDTO(
                "A101",
                "A102",
                10000.0,
                "INR",
                "USD",
                TransactionType.INTERNATIONAL_TRANSFER
        );

        try {
            TransactionResponseDTO response = engine.execute(request);

            System.out.println("Transaction ID: " + response.getTransactionId());
            System.out.println("Status: " + response.getStatus());
            System.out.println("Message: " + response.getMessage());
            System.out.println("Debited Amount: " + response.getDebitedAmount());
            System.out.println("Credited Amount: " + response.getCreditedAmount());

        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}