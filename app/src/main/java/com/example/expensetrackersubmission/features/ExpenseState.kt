package com.example.expensetrackersubmission.features

import com.example.expensetrackersubmission.domain.Category
import com.example.expensetrackersubmission.features.ExpenseUiModel

data class ExpenseState(
    val items: List<ExpenseUiModel> = emptyList(),
    val selectedCategory: Category? = null,
    val showAddSheet: Boolean = false,
    val errorMessage: String? = null
)
