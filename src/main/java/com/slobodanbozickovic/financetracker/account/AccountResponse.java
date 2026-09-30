package com.slobodanbozickovic.financetracker.account;

public record AccountResponse(
    Long id,
    String name,
    AccountType type
) {
}
