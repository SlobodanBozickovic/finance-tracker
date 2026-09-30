package com.slobodanbozickovic.financetracker.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AccountRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:17");

    static {
        postgres.start();
    }

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void shouldSaveAccount() {
        Account account =
                new Account("Main account", AccountType.BANK_ACCOUNT);

        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount.getId()).isNotNull();
        assertThat(savedAccount.getName()).isEqualTo("Main account");
        assertThat(savedAccount.getType()).isEqualTo(AccountType.BANK_ACCOUNT);
    }
}