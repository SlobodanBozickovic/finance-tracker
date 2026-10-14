package com.slobodanbozickovic.financetracker.category;

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
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping 
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest categoryRequest) {
        Category category = categoryService.createCategory(categoryRequest.name());
        return new CategoryResponse(category.getId(), category.getName());
    }

    @GetMapping
    public List<CategoryResponse> fetchAllCategories() {
        return categoryService.findAllCategories()
            .stream()
            .map(cat -> new CategoryResponse(cat.getId(), cat.getName()))
            .toList();
    }
}
