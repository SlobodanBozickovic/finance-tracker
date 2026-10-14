package com.slobodanbozickovic.financetracker.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest (
    @NotBlank 
    String name
){
}
