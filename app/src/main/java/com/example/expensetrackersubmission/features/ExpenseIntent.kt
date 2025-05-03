package com.example.expensetrackersubmission.features

import com.example.expensetrackersubmission.domain.Category

sealed class ExpenseIntent {
    object AddClicked : ExpenseIntent()
    data class EditClicked(val expense: ExpenseUiModel) : ExpenseIntent()
    object DismissAdd : ExpenseIntent()
    data class SaveExpense(
        val label: String,
        val amount: String,
        val category: Category
    ) : ExpenseIntent()
    data class SaveEdit(
        val id: Int,
        val label: String,
        val amount: String,
        val category: Category
    ) : ExpenseIntent()
    data class FilterByCategory(val category: Category?) : ExpenseIntent()

    data class DeleteClicked(val expense: ExpenseUiModel) : ExpenseIntent()

    data class AskDelete(val expense: ExpenseUiModel) : ExpenseIntent()
    object  CancelDelete : ExpenseIntent()
    data class ConfirmDelete(val expense: ExpenseUiModel): ExpenseIntent()
}
