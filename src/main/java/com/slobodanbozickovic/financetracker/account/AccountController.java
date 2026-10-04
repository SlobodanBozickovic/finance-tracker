package com.slobodanbozickovic.financetracker.account;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {

        Account account = accountService.createAccount(
            request.name(),
            request.type()
        );

        return new AccountResponse(account.getId(), account.getName(), account.getType());
    }

    @GetMapping 
    public List<AccountResponse> getAccounts() {
        return accountService.getAccounts()
            .stream()
            .map(account -> new AccountResponse(account.getId(), account.getName(), account.getType()))
            .toList();
    }
    
}
