package com.example.expensetrackersubmission.features

import com.example.expensetrackersubmission.domain.Category

sealed class ExpenseIntent {
    object AddClicked : ExpenseIntent()
    object DismissAdd : ExpenseIntent()
    data class SaveExpense(
        val label: String,
        val amount: String,
        val category: Category
    ) : ExpenseIntent()

    data class FilterByCategory(val category: Category?) : ExpenseIntent()

    data class DeleteClicked(val expense: ExpenseUiModel) : ExpenseIntent()
}
