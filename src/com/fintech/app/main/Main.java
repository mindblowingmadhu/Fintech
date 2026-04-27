package com.fintech.app.main;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.dto.TransactionResponseDTO;
import com.fintech.app.engine.TransactionEngine;
import com.fintech.app.enums.AccountStatus;
import com.fintech.app.enums.TransactionType;
import com.fintech.app.model.Account;
import com.fintech.app.model.User;
import com.fintech.app.repository.AccountRepository;
import com.fintech.app.repository.TransactionRepository;
import com.fintech.app.repository.UserRepository;
import com.fintech.app.service.CurrencyService;
import com.fintech.app.service.RiskService;
import com.fintech.app.service.TransactionService;
import com.fintech.app.service.ValidationService;

public class Main {

    public static void main(String[] args) {

        UserRepository userRepository = new UserRepository();
        AccountRepository accountRepository = new AccountRepository();
        TransactionRepository transactionRepository = new TransactionRepository();

        User user1 = new User("U101", "Madhu", "madhu@gmail.com", "9999999991");
        User user2 = new User("U102", "Coco", "coco@gmail.com", "9999999992");

        userRepository.save(user1);
        userRepository.save(user2);

        Account acc1 = new Account("A101", "U101", 1200000.0, "INR", AccountStatus.ACTIVE);
        Account acc2 = new Account("A102", "U102", 50000.0, "USD", AccountStatus.ACTIVE);

        accountRepository.save(acc1);
        accountRepository.save(acc2);

        ValidationService validationService = new ValidationService(accountRepository);
        RiskService riskService = new RiskService();
        CurrencyService currencyService = new CurrencyService();
        TransactionService transactionService =
                new TransactionService(accountRepository, transactionRepository, currencyService);

        TransactionEngine engine =
                new TransactionEngine(validationService, riskService, transactionService);

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

            System.out.println("Sender New Balance: " + accountRepository.findById("A101").getBalance());
            System.out.println("Receiver New Balance: " + accountRepository.findById("A102").getBalance());

        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}