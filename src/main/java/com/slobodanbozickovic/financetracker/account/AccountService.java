package com.slobodanbozickovic.financetracker.account;

import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(String name, AccountType type) {
        Account account = new Account(name, type);

        return accountRepository.save(account);
    }
}