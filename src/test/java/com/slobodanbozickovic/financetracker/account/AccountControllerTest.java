package com.slobodanbozickovic.financetracker.account;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate 
@Testcontainers
class AccountControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:17");

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreateAccount() {
        CreateAccountRequest request =
                new CreateAccountRequest(
                        "Main account",
                        AccountType.BANK_ACCOUNT
                );

        var response = restTemplate.postForEntity(
                "/api/accounts",
                request,
                AccountResponse.class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().name())
                .isEqualTo("Main account");

        assertThat(response.getBody().type())
                .isEqualTo(AccountType.BANK_ACCOUNT);

        assertThat(response.getBody().id())
                .isNotNull();
    }

    @Test
    void shouldRejectAccountWithBlankName() {
    CreateAccountRequest request =
            new CreateAccountRequest(
                    "",
                    AccountType.BANK_ACCOUNT
            );

    var response = restTemplate.postForEntity(
            "/api/accounts",
            request,
            String.class
    );

    assertThat(response.getStatusCode().value()).isEqualTo(400);
}
}