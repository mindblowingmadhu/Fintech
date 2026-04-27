package com.fintech.app.repository;

import java.util.HashMap;
import java.util.Map;

import com.fintech.app.repository.interfaces.AccountRepositoryInterface;
import com.fintech.app.model.Account;

public class AccountRepository implements AccountRepositoryInterface {

    private final Map<String, Account> accountStore = new HashMap<>();

    @Override
    public void save(Account account) {
        accountStore.put(account.getAccountId(), account);
    }

    @Override
    public Account findById(String accountId) {
        return accountStore.get(accountId);
    }

    @Override
    public void update(Account account) {
        // In-memory update = overwrite existing value
        accountStore.put(account.getAccountId(), account);
    }
}