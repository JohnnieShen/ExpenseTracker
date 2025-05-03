package com.example.expensetrackersubmission.features

import com.example.expensetrackersubmission.domain.Category
import com.example.expensetrackersubmission.features.ExpenseUiModel

data class ExpenseState(
    val items: List<ExpenseUiModel> = emptyList(),
    val selectedCategory: Category? = null,
    val editing: ExpenseUiModel? = null,
    val showSheet: Boolean = false,
    val pendingDelete: ExpenseUiModel? = null,
    val errorMessage: String? = null
)
