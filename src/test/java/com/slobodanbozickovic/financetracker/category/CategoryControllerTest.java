package com.slobodanbozickovic.financetracker.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate 
@Testcontainers 
public class CategoryControllerTest {

    @Container 
    @ServiceConnection 
    static PostgreSQLContainer postgres = 
        new PostgreSQLContainer("postgres:17");

    @Autowired 
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test 
    void shouldCreateCategory() {
        CreateCategoryRequest request = new CreateCategoryRequest("Paprike");

        var response = restTemplate.postForEntity("/api/categories", request, CategoryResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(201);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Paprike");

    }

    @Test
    void shouldRejectCategoryWithBlankName() {
        CreateCategoryRequest request = 
            new CreateCategoryRequest("");

        var response = restTemplate.postForEntity(
            "/api/categories", 
            request,
            String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void shouldReturnCategories() {
        Category groceries = categoryRepository.save(
                new Category("Groceries")
        );

        Category transport = categoryRepository.save(
                new Category("Transport")
        );

        var response = restTemplate.getForEntity(
                "/api/categories",
                CategoryResponse[].class
        );

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody())
                .extracting(CategoryResponse::id)
                .contains(groceries.getId(), transport.getId());
}
    
}
