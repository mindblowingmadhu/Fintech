package com.fintech.app.service;

import java.time.LocalDateTime;

import com.fintech.app.aiml.RiskEngine;
import com.fintech.app.aiml.RiskResult;
import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.enums.TransactionStatus;
import com.fintech.app.model.Account;
import com.fintech.app.model.Transaction;
import com.fintech.app.model.TransactionResult;
import com.fintech.app.repository.interfaces.AccountRepositoryInterface;
import com.fintech.app.repository.interfaces.TransactionRepositoryInterface;
import com.fintech.app.util.IdGenerator;

public class TransactionService {

    private final AccountRepositoryInterface accountRepository;
    private final TransactionRepositoryInterface transactionRepository;
    private final CurrencyService currencyService;

    public TransactionService(AccountRepositoryInterface accountRepository,
                              TransactionRepositoryInterface transactionRepository,
                              CurrencyService currencyService) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.currencyService = currencyService;
    }

    public TransactionResult processTransaction(TransactionRequestDTO request) {

        Account source = accountRepository.findById(request.getSourceAccountId());
        Account destination = accountRepository.findById(request.getDestinationAccountId());

        if (source == null || destination == null) {
            throw new RuntimeException("Source or destination account not found");
        }

        double creditedAmount = currencyService.convert(
                request.getSourceCurrency(),
                request.getTargetCurrency(),
                request.getAmount()
        );

        RiskEngine riskEngine = new RiskEngine();

        RiskResult riskResult = riskEngine.analyseTransaction(
                request.getAmount(),
                request.getSourceCurrency(),
                0
        );

        if (riskResult.isRisky()) {

            Transaction blockedTxn = new Transaction(
                    IdGenerator.generateId("TXN"),
                    request.getSourceAccountId(),
                    request.getDestinationAccountId(),
                    request.getAmount(),
                    request.getSourceCurrency(),
                    request.getTargetCurrency(),
                    request.getTransactionType(),
                    TransactionStatus.BLOCKED,
                    LocalDateTime.now()
            );

            transactionRepository.save(blockedTxn);

            System.out.println("Transaction BLOCKED");
            System.out.println("Risk Score: " + riskResult.getRiskScore());
            System.out.println("Reason: " + riskResult.getReason());

            return new TransactionResult(blockedTxn, 0);
        }

        source.debit(request.getAmount());
        destination.credit(creditedAmount);

        accountRepository.update(source);
        accountRepository.update(destination);

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

        transactionRepository.save(transaction);

        return new TransactionResult(transaction, creditedAmount);
    }
}