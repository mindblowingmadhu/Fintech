package com.fintech.app.service;

import com.fintech.app.dto.TransactionRequestDTO;
import com.fintech.app.enums.AccountStatus;
import com.fintech.app.exception.AccountNotFoundException;
import com.fintech.app.exception.InsufficientBalanceException;
import com.fintech.app.exception.InvalidTransactionException;
import com.fintech.app.model.Account;
import com.fintech.app.repository.interfaces.AccountRepositoryInterface;

public class ValidationService {

    private final AccountRepositoryInterface accountRepository;

    public ValidationService(AccountRepositoryInterface accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void validateTransaction(TransactionRequestDTO request) {
        if (request.getAmount() <= 0) {
            throw new InvalidTransactionException("Amount must be greater than zero.");
        }

        Account source = accountRepository.findById(request.getSourceAccountId());
        Account destination = accountRepository.findById(request.getDestinationAccountId());

        if (source == null) {
            throw new AccountNotFoundException("Source account not found.");
        }

        if (destination == null) {
            throw new AccountNotFoundException("Destination account not found.");
        }

        if (source.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidTransactionException("Source account is not active.");
        }

        if (destination.getStatus() != AccountStatus.ACTIVE) {
            throw new InvalidTransactionException("Destination account is not active.");
        }

        if (source.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance in source account.");
        }
    }
}